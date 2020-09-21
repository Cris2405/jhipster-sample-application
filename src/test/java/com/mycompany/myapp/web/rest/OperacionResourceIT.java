package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FirstApp;
import com.mycompany.myapp.domain.Operacion;
import com.mycompany.myapp.repository.OperacionRepository;

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
 * Integration tests for the {@link OperacionResource} REST controller.
 */
@SpringBootTest(classes = FirstApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OperacionResourceIT {

    private static final Integer DEFAULT_IDOP = 1;
    private static final Integer UPDATED_IDOP = 2;

    private static final String DEFAULT_DESPEGUE = "AAAAAAAAAA";
    private static final String UPDATED_DESPEGUE = "BBBBBBBBBB";

    private static final String DEFAULT_ATERRIZAJE = "AAAAAAAAAA";
    private static final String UPDATED_ATERRIZAJE = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDAVION = 1;
    private static final Integer UPDATED_IDAVION = 2;

    @Autowired
    private OperacionRepository operacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperacionMockMvc;

    private Operacion operacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operacion createEntity(EntityManager em) {
        Operacion operacion = new Operacion()
            .idop(DEFAULT_IDOP)
            .despegue(DEFAULT_DESPEGUE)
            .aterrizaje(DEFAULT_ATERRIZAJE)
            .fecha(DEFAULT_FECHA)
            .idavion(DEFAULT_IDAVION);
        return operacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operacion createUpdatedEntity(EntityManager em) {
        Operacion operacion = new Operacion()
            .idop(UPDATED_IDOP)
            .despegue(UPDATED_DESPEGUE)
            .aterrizaje(UPDATED_ATERRIZAJE)
            .fecha(UPDATED_FECHA)
            .idavion(UPDATED_IDAVION);
        return operacion;
    }

    @BeforeEach
    public void initTest() {
        operacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperacion() throws Exception {
        int databaseSizeBeforeCreate = operacionRepository.findAll().size();
        // Create the Operacion
        restOperacionMockMvc.perform(post("/api/operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operacion)))
            .andExpect(status().isCreated());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeCreate + 1);
        Operacion testOperacion = operacionList.get(operacionList.size() - 1);
        assertThat(testOperacion.getIdop()).isEqualTo(DEFAULT_IDOP);
        assertThat(testOperacion.getDespegue()).isEqualTo(DEFAULT_DESPEGUE);
        assertThat(testOperacion.getAterrizaje()).isEqualTo(DEFAULT_ATERRIZAJE);
        assertThat(testOperacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOperacion.getIdavion()).isEqualTo(DEFAULT_IDAVION);
    }

    @Test
    @Transactional
    public void createOperacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operacionRepository.findAll().size();

        // Create the Operacion with an existing ID
        operacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperacionMockMvc.perform(post("/api/operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operacion)))
            .andExpect(status().isBadRequest());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOperacions() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        // Get all the operacionList
        restOperacionMockMvc.perform(get("/api/operacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idop").value(hasItem(DEFAULT_IDOP)))
            .andExpect(jsonPath("$.[*].despegue").value(hasItem(DEFAULT_DESPEGUE)))
            .andExpect(jsonPath("$.[*].aterrizaje").value(hasItem(DEFAULT_ATERRIZAJE)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.[*].idavion").value(hasItem(DEFAULT_IDAVION)));
    }
    
    @Test
    @Transactional
    public void getOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        // Get the operacion
        restOperacionMockMvc.perform(get("/api/operacions/{id}", operacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operacion.getId().intValue()))
            .andExpect(jsonPath("$.idop").value(DEFAULT_IDOP))
            .andExpect(jsonPath("$.despegue").value(DEFAULT_DESPEGUE))
            .andExpect(jsonPath("$.aterrizaje").value(DEFAULT_ATERRIZAJE))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA))
            .andExpect(jsonPath("$.idavion").value(DEFAULT_IDAVION));
    }
    @Test
    @Transactional
    public void getNonExistingOperacion() throws Exception {
        // Get the operacion
        restOperacionMockMvc.perform(get("/api/operacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        int databaseSizeBeforeUpdate = operacionRepository.findAll().size();

        // Update the operacion
        Operacion updatedOperacion = operacionRepository.findById(operacion.getId()).get();
        // Disconnect from session so that the updates on updatedOperacion are not directly saved in db
        em.detach(updatedOperacion);
        updatedOperacion
            .idop(UPDATED_IDOP)
            .despegue(UPDATED_DESPEGUE)
            .aterrizaje(UPDATED_ATERRIZAJE)
            .fecha(UPDATED_FECHA)
            .idavion(UPDATED_IDAVION);

        restOperacionMockMvc.perform(put("/api/operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperacion)))
            .andExpect(status().isOk());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeUpdate);
        Operacion testOperacion = operacionList.get(operacionList.size() - 1);
        assertThat(testOperacion.getIdop()).isEqualTo(UPDATED_IDOP);
        assertThat(testOperacion.getDespegue()).isEqualTo(UPDATED_DESPEGUE);
        assertThat(testOperacion.getAterrizaje()).isEqualTo(UPDATED_ATERRIZAJE);
        assertThat(testOperacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOperacion.getIdavion()).isEqualTo(UPDATED_IDAVION);
    }

    @Test
    @Transactional
    public void updateNonExistingOperacion() throws Exception {
        int databaseSizeBeforeUpdate = operacionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperacionMockMvc.perform(put("/api/operacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operacion)))
            .andExpect(status().isBadRequest());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        int databaseSizeBeforeDelete = operacionRepository.findAll().size();

        // Delete the operacion
        restOperacionMockMvc.perform(delete("/api/operacions/{id}", operacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
