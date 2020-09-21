package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FirstApp;
import com.mycompany.myapp.domain.Aeropuerto;
import com.mycompany.myapp.repository.AeropuertoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AeropuertoResource} REST controller.
 */
@SpringBootTest(classes = FirstApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AeropuertoResourceIT {

    private static final Integer DEFAULT_IDAERO = 1;
    private static final Integer UPDATED_IDAERO = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAeropuertoMockMvc;

    private Aeropuerto aeropuerto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeropuerto createEntity(EntityManager em) {
        Aeropuerto aeropuerto = new Aeropuerto()
            .idaero(DEFAULT_IDAERO)
            .nombre(DEFAULT_NOMBRE)
            .pais(DEFAULT_PAIS)
            .ciudad(DEFAULT_CIUDAD)
            .direccion(DEFAULT_DIRECCION);
        return aeropuerto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeropuerto createUpdatedEntity(EntityManager em) {
        Aeropuerto aeropuerto = new Aeropuerto()
            .idaero(UPDATED_IDAERO)
            .nombre(UPDATED_NOMBRE)
            .pais(UPDATED_PAIS)
            .ciudad(UPDATED_CIUDAD)
            .direccion(UPDATED_DIRECCION);
        return aeropuerto;
    }

    @BeforeEach
    public void initTest() {
        aeropuerto = createEntity(em);
    }

    @Test
    @Transactional
    public void createAeropuerto() throws Exception {
        int databaseSizeBeforeCreate = aeropuertoRepository.findAll().size();
        // Create the Aeropuerto
        restAeropuertoMockMvc.perform(post("/api/aeropuertos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isCreated());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeCreate + 1);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getIdaero()).isEqualTo(DEFAULT_IDAERO);
        assertThat(testAeropuerto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAeropuerto.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testAeropuerto.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void createAeropuertoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aeropuertoRepository.findAll().size();

        // Create the Aeropuerto with an existing ID
        aeropuerto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAeropuertoMockMvc.perform(post("/api/aeropuertos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAeropuertos() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get all the aeropuertoList
        restAeropuertoMockMvc.perform(get("/api/aeropuertos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeropuerto.getId().intValue())))
            .andExpect(jsonPath("$.[*].idaero").value(hasItem(DEFAULT_IDAERO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }
    
    @Test
    @Transactional
    public void getAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        // Get the aeropuerto
        restAeropuertoMockMvc.perform(get("/api/aeropuertos/{id}", aeropuerto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aeropuerto.getId().intValue()))
            .andExpect(jsonPath("$.idaero").value(DEFAULT_IDAERO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }
    @Test
    @Transactional
    public void getNonExistingAeropuerto() throws Exception {
        // Get the aeropuerto
        restAeropuertoMockMvc.perform(get("/api/aeropuertos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // Update the aeropuerto
        Aeropuerto updatedAeropuerto = aeropuertoRepository.findById(aeropuerto.getId()).get();
        // Disconnect from session so that the updates on updatedAeropuerto are not directly saved in db
        em.detach(updatedAeropuerto);
        updatedAeropuerto
            .idaero(UPDATED_IDAERO)
            .nombre(UPDATED_NOMBRE)
            .pais(UPDATED_PAIS)
            .ciudad(UPDATED_CIUDAD)
            .direccion(UPDATED_DIRECCION);

        restAeropuertoMockMvc.perform(put("/api/aeropuertos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAeropuerto)))
            .andExpect(status().isOk());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
        Aeropuerto testAeropuerto = aeropuertoList.get(aeropuertoList.size() - 1);
        assertThat(testAeropuerto.getIdaero()).isEqualTo(UPDATED_IDAERO);
        assertThat(testAeropuerto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAeropuerto.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testAeropuerto.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testAeropuerto.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void updateNonExistingAeropuerto() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeropuertoMockMvc.perform(put("/api/aeropuertos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeropuerto)))
            .andExpect(status().isBadRequest());

        // Validate the Aeropuerto in the database
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAeropuerto() throws Exception {
        // Initialize the database
        aeropuertoRepository.saveAndFlush(aeropuerto);

        int databaseSizeBeforeDelete = aeropuertoRepository.findAll().size();

        // Delete the aeropuerto
        restAeropuertoMockMvc.perform(delete("/api/aeropuertos/{id}", aeropuerto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aeropuerto> aeropuertoList = aeropuertoRepository.findAll();
        assertThat(aeropuertoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
