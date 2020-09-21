package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FirstApp;
import com.mycompany.myapp.domain.Pasajero;
import com.mycompany.myapp.repository.PasajeroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PasajeroResource} REST controller.
 */
@SpringBootTest(classes = FirstApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PasajeroResourceIT {

    private static final Integer DEFAULT_IDPAS = 1;
    private static final Integer UPDATED_IDPAS = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDT = 1;
    private static final Integer UPDATED_IDT = 2;

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Mock
    private PasajeroRepository pasajeroRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPasajeroMockMvc;

    private Pasajero pasajero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pasajero createEntity(EntityManager em) {
        Pasajero pasajero = new Pasajero()
            .idpas(DEFAULT_IDPAS)
            .nombre(DEFAULT_NOMBRE)
            .identificacion(DEFAULT_IDENTIFICACION)
            .idt(DEFAULT_IDT);
        return pasajero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pasajero createUpdatedEntity(EntityManager em) {
        Pasajero pasajero = new Pasajero()
            .idpas(UPDATED_IDPAS)
            .nombre(UPDATED_NOMBRE)
            .identificacion(UPDATED_IDENTIFICACION)
            .idt(UPDATED_IDT);
        return pasajero;
    }

    @BeforeEach
    public void initTest() {
        pasajero = createEntity(em);
    }

    @Test
    @Transactional
    public void createPasajero() throws Exception {
        int databaseSizeBeforeCreate = pasajeroRepository.findAll().size();
        // Create the Pasajero
        restPasajeroMockMvc.perform(post("/api/pasajeros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pasajero)))
            .andExpect(status().isCreated());

        // Validate the Pasajero in the database
        List<Pasajero> pasajeroList = pasajeroRepository.findAll();
        assertThat(pasajeroList).hasSize(databaseSizeBeforeCreate + 1);
        Pasajero testPasajero = pasajeroList.get(pasajeroList.size() - 1);
        assertThat(testPasajero.getIdpas()).isEqualTo(DEFAULT_IDPAS);
        assertThat(testPasajero.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPasajero.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testPasajero.getIdt()).isEqualTo(DEFAULT_IDT);
    }

    @Test
    @Transactional
    public void createPasajeroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pasajeroRepository.findAll().size();

        // Create the Pasajero with an existing ID
        pasajero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPasajeroMockMvc.perform(post("/api/pasajeros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pasajero)))
            .andExpect(status().isBadRequest());

        // Validate the Pasajero in the database
        List<Pasajero> pasajeroList = pasajeroRepository.findAll();
        assertThat(pasajeroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPasajeros() throws Exception {
        // Initialize the database
        pasajeroRepository.saveAndFlush(pasajero);

        // Get all the pasajeroList
        restPasajeroMockMvc.perform(get("/api/pasajeros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pasajero.getId().intValue())))
            .andExpect(jsonPath("$.[*].idpas").value(hasItem(DEFAULT_IDPAS)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].idt").value(hasItem(DEFAULT_IDT)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPasajerosWithEagerRelationshipsIsEnabled() throws Exception {
        when(pasajeroRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPasajeroMockMvc.perform(get("/api/pasajeros?eagerload=true"))
            .andExpect(status().isOk());

        verify(pasajeroRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPasajerosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pasajeroRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPasajeroMockMvc.perform(get("/api/pasajeros?eagerload=true"))
            .andExpect(status().isOk());

        verify(pasajeroRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPasajero() throws Exception {
        // Initialize the database
        pasajeroRepository.saveAndFlush(pasajero);

        // Get the pasajero
        restPasajeroMockMvc.perform(get("/api/pasajeros/{id}", pasajero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pasajero.getId().intValue()))
            .andExpect(jsonPath("$.idpas").value(DEFAULT_IDPAS))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION))
            .andExpect(jsonPath("$.idt").value(DEFAULT_IDT));
    }
    @Test
    @Transactional
    public void getNonExistingPasajero() throws Exception {
        // Get the pasajero
        restPasajeroMockMvc.perform(get("/api/pasajeros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePasajero() throws Exception {
        // Initialize the database
        pasajeroRepository.saveAndFlush(pasajero);

        int databaseSizeBeforeUpdate = pasajeroRepository.findAll().size();

        // Update the pasajero
        Pasajero updatedPasajero = pasajeroRepository.findById(pasajero.getId()).get();
        // Disconnect from session so that the updates on updatedPasajero are not directly saved in db
        em.detach(updatedPasajero);
        updatedPasajero
            .idpas(UPDATED_IDPAS)
            .nombre(UPDATED_NOMBRE)
            .identificacion(UPDATED_IDENTIFICACION)
            .idt(UPDATED_IDT);

        restPasajeroMockMvc.perform(put("/api/pasajeros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPasajero)))
            .andExpect(status().isOk());

        // Validate the Pasajero in the database
        List<Pasajero> pasajeroList = pasajeroRepository.findAll();
        assertThat(pasajeroList).hasSize(databaseSizeBeforeUpdate);
        Pasajero testPasajero = pasajeroList.get(pasajeroList.size() - 1);
        assertThat(testPasajero.getIdpas()).isEqualTo(UPDATED_IDPAS);
        assertThat(testPasajero.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPasajero.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testPasajero.getIdt()).isEqualTo(UPDATED_IDT);
    }

    @Test
    @Transactional
    public void updateNonExistingPasajero() throws Exception {
        int databaseSizeBeforeUpdate = pasajeroRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPasajeroMockMvc.perform(put("/api/pasajeros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pasajero)))
            .andExpect(status().isBadRequest());

        // Validate the Pasajero in the database
        List<Pasajero> pasajeroList = pasajeroRepository.findAll();
        assertThat(pasajeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePasajero() throws Exception {
        // Initialize the database
        pasajeroRepository.saveAndFlush(pasajero);

        int databaseSizeBeforeDelete = pasajeroRepository.findAll().size();

        // Delete the pasajero
        restPasajeroMockMvc.perform(delete("/api/pasajeros/{id}", pasajero.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pasajero> pasajeroList = pasajeroRepository.findAll();
        assertThat(pasajeroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
