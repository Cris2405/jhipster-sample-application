package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FirstApp;
import com.mycompany.myapp.domain.Tiquete;
import com.mycompany.myapp.repository.TiqueteRepository;

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
 * Integration tests for the {@link TiqueteResource} REST controller.
 */
@SpringBootTest(classes = FirstApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TiqueteResourceIT {

    private static final Integer DEFAULT_IDT = 1;
    private static final Integer UPDATED_IDT = 2;

    private static final String DEFAULT_ASIENTO = "AAAAAAAAAA";
    private static final String UPDATED_ASIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    @Autowired
    private TiqueteRepository tiqueteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTiqueteMockMvc;

    private Tiquete tiquete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tiquete createEntity(EntityManager em) {
        Tiquete tiquete = new Tiquete()
            .idt(DEFAULT_IDT)
            .asiento(DEFAULT_ASIENTO)
            .fecha(DEFAULT_FECHA);
        return tiquete;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tiquete createUpdatedEntity(EntityManager em) {
        Tiquete tiquete = new Tiquete()
            .idt(UPDATED_IDT)
            .asiento(UPDATED_ASIENTO)
            .fecha(UPDATED_FECHA);
        return tiquete;
    }

    @BeforeEach
    public void initTest() {
        tiquete = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiquete() throws Exception {
        int databaseSizeBeforeCreate = tiqueteRepository.findAll().size();
        // Create the Tiquete
        restTiqueteMockMvc.perform(post("/api/tiquetes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiquete)))
            .andExpect(status().isCreated());

        // Validate the Tiquete in the database
        List<Tiquete> tiqueteList = tiqueteRepository.findAll();
        assertThat(tiqueteList).hasSize(databaseSizeBeforeCreate + 1);
        Tiquete testTiquete = tiqueteList.get(tiqueteList.size() - 1);
        assertThat(testTiquete.getIdt()).isEqualTo(DEFAULT_IDT);
        assertThat(testTiquete.getAsiento()).isEqualTo(DEFAULT_ASIENTO);
        assertThat(testTiquete.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createTiqueteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiqueteRepository.findAll().size();

        // Create the Tiquete with an existing ID
        tiquete.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiqueteMockMvc.perform(post("/api/tiquetes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiquete)))
            .andExpect(status().isBadRequest());

        // Validate the Tiquete in the database
        List<Tiquete> tiqueteList = tiqueteRepository.findAll();
        assertThat(tiqueteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTiquetes() throws Exception {
        // Initialize the database
        tiqueteRepository.saveAndFlush(tiquete);

        // Get all the tiqueteList
        restTiqueteMockMvc.perform(get("/api/tiquetes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiquete.getId().intValue())))
            .andExpect(jsonPath("$.[*].idt").value(hasItem(DEFAULT_IDT)))
            .andExpect(jsonPath("$.[*].asiento").value(hasItem(DEFAULT_ASIENTO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA)));
    }
    
    @Test
    @Transactional
    public void getTiquete() throws Exception {
        // Initialize the database
        tiqueteRepository.saveAndFlush(tiquete);

        // Get the tiquete
        restTiqueteMockMvc.perform(get("/api/tiquetes/{id}", tiquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tiquete.getId().intValue()))
            .andExpect(jsonPath("$.idt").value(DEFAULT_IDT))
            .andExpect(jsonPath("$.asiento").value(DEFAULT_ASIENTO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA));
    }
    @Test
    @Transactional
    public void getNonExistingTiquete() throws Exception {
        // Get the tiquete
        restTiqueteMockMvc.perform(get("/api/tiquetes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiquete() throws Exception {
        // Initialize the database
        tiqueteRepository.saveAndFlush(tiquete);

        int databaseSizeBeforeUpdate = tiqueteRepository.findAll().size();

        // Update the tiquete
        Tiquete updatedTiquete = tiqueteRepository.findById(tiquete.getId()).get();
        // Disconnect from session so that the updates on updatedTiquete are not directly saved in db
        em.detach(updatedTiquete);
        updatedTiquete
            .idt(UPDATED_IDT)
            .asiento(UPDATED_ASIENTO)
            .fecha(UPDATED_FECHA);

        restTiqueteMockMvc.perform(put("/api/tiquetes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTiquete)))
            .andExpect(status().isOk());

        // Validate the Tiquete in the database
        List<Tiquete> tiqueteList = tiqueteRepository.findAll();
        assertThat(tiqueteList).hasSize(databaseSizeBeforeUpdate);
        Tiquete testTiquete = tiqueteList.get(tiqueteList.size() - 1);
        assertThat(testTiquete.getIdt()).isEqualTo(UPDATED_IDT);
        assertThat(testTiquete.getAsiento()).isEqualTo(UPDATED_ASIENTO);
        assertThat(testTiquete.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingTiquete() throws Exception {
        int databaseSizeBeforeUpdate = tiqueteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiqueteMockMvc.perform(put("/api/tiquetes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiquete)))
            .andExpect(status().isBadRequest());

        // Validate the Tiquete in the database
        List<Tiquete> tiqueteList = tiqueteRepository.findAll();
        assertThat(tiqueteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTiquete() throws Exception {
        // Initialize the database
        tiqueteRepository.saveAndFlush(tiquete);

        int databaseSizeBeforeDelete = tiqueteRepository.findAll().size();

        // Delete the tiquete
        restTiqueteMockMvc.perform(delete("/api/tiquetes/{id}", tiquete.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tiquete> tiqueteList = tiqueteRepository.findAll();
        assertThat(tiqueteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
