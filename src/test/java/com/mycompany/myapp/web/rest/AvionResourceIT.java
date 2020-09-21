package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FirstApp;
import com.mycompany.myapp.domain.Avion;
import com.mycompany.myapp.repository.AvionRepository;

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
 * Integration tests for the {@link AvionResource} REST controller.
 */
@SpringBootTest(classes = FirstApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvionResourceIT {

    private static final Integer DEFAULT_IDAVION = 1;
    private static final Integer UPDATED_IDAVION = 2;

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACIDAD = 1;
    private static final Integer UPDATED_CAPACIDAD = 2;

    private static final String DEFAULT_IDAERO = "AAAAAAAAAA";
    private static final String UPDATED_IDAERO = "BBBBBBBBBB";

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvionMockMvc;

    private Avion avion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createEntity(EntityManager em) {
        Avion avion = new Avion()
            .idavion(DEFAULT_IDAVION)
            .modelo(DEFAULT_MODELO)
            .capacidad(DEFAULT_CAPACIDAD)
            .idaero(DEFAULT_IDAERO);
        return avion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createUpdatedEntity(EntityManager em) {
        Avion avion = new Avion()
            .idavion(UPDATED_IDAVION)
            .modelo(UPDATED_MODELO)
            .capacidad(UPDATED_CAPACIDAD)
            .idaero(UPDATED_IDAERO);
        return avion;
    }

    @BeforeEach
    public void initTest() {
        avion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvion() throws Exception {
        int databaseSizeBeforeCreate = avionRepository.findAll().size();
        // Create the Avion
        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isCreated());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate + 1);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getIdavion()).isEqualTo(DEFAULT_IDAVION);
        assertThat(testAvion.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testAvion.getCapacidad()).isEqualTo(DEFAULT_CAPACIDAD);
        assertThat(testAvion.getIdaero()).isEqualTo(DEFAULT_IDAERO);
    }

    @Test
    @Transactional
    public void createAvionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avionRepository.findAll().size();

        // Create the Avion with an existing ID
        avion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvions() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList
        restAvionMockMvc.perform(get("/api/avions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idavion").value(hasItem(DEFAULT_IDAVION)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD)))
            .andExpect(jsonPath("$.[*].idaero").value(hasItem(DEFAULT_IDAERO)));
    }
    
    @Test
    @Transactional
    public void getAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get the avion
        restAvionMockMvc.perform(get("/api/avions/{id}", avion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avion.getId().intValue()))
            .andExpect(jsonPath("$.idavion").value(DEFAULT_IDAVION))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.capacidad").value(DEFAULT_CAPACIDAD))
            .andExpect(jsonPath("$.idaero").value(DEFAULT_IDAERO));
    }
    @Test
    @Transactional
    public void getNonExistingAvion() throws Exception {
        // Get the avion
        restAvionMockMvc.perform(get("/api/avions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion
        Avion updatedAvion = avionRepository.findById(avion.getId()).get();
        // Disconnect from session so that the updates on updatedAvion are not directly saved in db
        em.detach(updatedAvion);
        updatedAvion
            .idavion(UPDATED_IDAVION)
            .modelo(UPDATED_MODELO)
            .capacidad(UPDATED_CAPACIDAD)
            .idaero(UPDATED_IDAERO);

        restAvionMockMvc.perform(put("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvion)))
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getIdavion()).isEqualTo(UPDATED_IDAVION);
        assertThat(testAvion.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testAvion.getCapacidad()).isEqualTo(UPDATED_CAPACIDAD);
        assertThat(testAvion.getIdaero()).isEqualTo(UPDATED_IDAERO);
    }

    @Test
    @Transactional
    public void updateNonExistingAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionMockMvc.perform(put("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeDelete = avionRepository.findAll().size();

        // Delete the avion
        restAvionMockMvc.perform(delete("/api/avions/{id}", avion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
