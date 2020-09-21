package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FirstApp;
import com.mycompany.myapp.domain.Vuelo;
import com.mycompany.myapp.repository.VueloRepository;

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
 * Integration tests for the {@link VueloResource} REST controller.
 */
@SpringBootTest(classes = FirstApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VueloResourceIT {

    private static final Integer DEFAULT_IDVUELO = 1;
    private static final Integer UPDATED_IDVUELO = 2;

    private static final String DEFAULT_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEN = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_DESTINO = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDPAS = 1;
    private static final Integer UPDATED_IDPAS = 2;

    @Autowired
    private VueloRepository vueloRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVueloMockMvc;

    private Vuelo vuelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vuelo createEntity(EntityManager em) {
        Vuelo vuelo = new Vuelo()
            .idvuelo(DEFAULT_IDVUELO)
            .origen(DEFAULT_ORIGEN)
            .destino(DEFAULT_DESTINO)
            .idpas(DEFAULT_IDPAS);
        return vuelo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vuelo createUpdatedEntity(EntityManager em) {
        Vuelo vuelo = new Vuelo()
            .idvuelo(UPDATED_IDVUELO)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .idpas(UPDATED_IDPAS);
        return vuelo;
    }

    @BeforeEach
    public void initTest() {
        vuelo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVuelo() throws Exception {
        int databaseSizeBeforeCreate = vueloRepository.findAll().size();
        // Create the Vuelo
        restVueloMockMvc.perform(post("/api/vuelos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vuelo)))
            .andExpect(status().isCreated());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeCreate + 1);
        Vuelo testVuelo = vueloList.get(vueloList.size() - 1);
        assertThat(testVuelo.getIdvuelo()).isEqualTo(DEFAULT_IDVUELO);
        assertThat(testVuelo.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
        assertThat(testVuelo.getDestino()).isEqualTo(DEFAULT_DESTINO);
        assertThat(testVuelo.getIdpas()).isEqualTo(DEFAULT_IDPAS);
    }

    @Test
    @Transactional
    public void createVueloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vueloRepository.findAll().size();

        // Create the Vuelo with an existing ID
        vuelo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVueloMockMvc.perform(post("/api/vuelos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vuelo)))
            .andExpect(status().isBadRequest());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVuelos() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        // Get all the vueloList
        restVueloMockMvc.perform(get("/api/vuelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vuelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idvuelo").value(hasItem(DEFAULT_IDVUELO)))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN)))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO)))
            .andExpect(jsonPath("$.[*].idpas").value(hasItem(DEFAULT_IDPAS)));
    }
    
    @Test
    @Transactional
    public void getVuelo() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        // Get the vuelo
        restVueloMockMvc.perform(get("/api/vuelos/{id}", vuelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vuelo.getId().intValue()))
            .andExpect(jsonPath("$.idvuelo").value(DEFAULT_IDVUELO))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN))
            .andExpect(jsonPath("$.destino").value(DEFAULT_DESTINO))
            .andExpect(jsonPath("$.idpas").value(DEFAULT_IDPAS));
    }
    @Test
    @Transactional
    public void getNonExistingVuelo() throws Exception {
        // Get the vuelo
        restVueloMockMvc.perform(get("/api/vuelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVuelo() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();

        // Update the vuelo
        Vuelo updatedVuelo = vueloRepository.findById(vuelo.getId()).get();
        // Disconnect from session so that the updates on updatedVuelo are not directly saved in db
        em.detach(updatedVuelo);
        updatedVuelo
            .idvuelo(UPDATED_IDVUELO)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .idpas(UPDATED_IDPAS);

        restVueloMockMvc.perform(put("/api/vuelos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVuelo)))
            .andExpect(status().isOk());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
        Vuelo testVuelo = vueloList.get(vueloList.size() - 1);
        assertThat(testVuelo.getIdvuelo()).isEqualTo(UPDATED_IDVUELO);
        assertThat(testVuelo.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testVuelo.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testVuelo.getIdpas()).isEqualTo(UPDATED_IDPAS);
    }

    @Test
    @Transactional
    public void updateNonExistingVuelo() throws Exception {
        int databaseSizeBeforeUpdate = vueloRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVueloMockMvc.perform(put("/api/vuelos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vuelo)))
            .andExpect(status().isBadRequest());

        // Validate the Vuelo in the database
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVuelo() throws Exception {
        // Initialize the database
        vueloRepository.saveAndFlush(vuelo);

        int databaseSizeBeforeDelete = vueloRepository.findAll().size();

        // Delete the vuelo
        restVueloMockMvc.perform(delete("/api/vuelos/{id}", vuelo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vuelo> vueloList = vueloRepository.findAll();
        assertThat(vueloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
