package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Aeropuerto;
import com.mycompany.myapp.repository.AeropuertoRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Aeropuerto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AeropuertoResource {

    private final Logger log = LoggerFactory.getLogger(AeropuertoResource.class);

    private static final String ENTITY_NAME = "aeropuerto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoResource(AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
    }

    /**
     * {@code POST  /aeropuertos} : Create a new aeropuerto.
     *
     * @param aeropuerto the aeropuerto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aeropuerto, or with status {@code 400 (Bad Request)} if the aeropuerto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aeropuertos")
    public ResponseEntity<Aeropuerto> createAeropuerto(@RequestBody Aeropuerto aeropuerto) throws URISyntaxException {
        log.debug("REST request to save Aeropuerto : {}", aeropuerto);
        if (aeropuerto.getId() != null) {
            throw new BadRequestAlertException("A new aeropuerto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aeropuerto result = aeropuertoRepository.save(aeropuerto);
        return ResponseEntity.created(new URI("/api/aeropuertos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aeropuertos} : Updates an existing aeropuerto.
     *
     * @param aeropuerto the aeropuerto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeropuerto,
     * or with status {@code 400 (Bad Request)} if the aeropuerto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aeropuerto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aeropuertos")
    public ResponseEntity<Aeropuerto> updateAeropuerto(@RequestBody Aeropuerto aeropuerto) throws URISyntaxException {
        log.debug("REST request to update Aeropuerto : {}", aeropuerto);
        if (aeropuerto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Aeropuerto result = aeropuertoRepository.save(aeropuerto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aeropuerto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aeropuertos} : get all the aeropuertos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aeropuertos in body.
     */
    @GetMapping("/aeropuertos")
    public List<Aeropuerto> getAllAeropuertos() {
        log.debug("REST request to get all Aeropuertos");
        return aeropuertoRepository.findAll();
    }

    /**
     * {@code GET  /aeropuertos/:id} : get the "id" aeropuerto.
     *
     * @param id the id of the aeropuerto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aeropuerto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aeropuertos/{id}")
    public ResponseEntity<Aeropuerto> getAeropuerto(@PathVariable Long id) {
        log.debug("REST request to get Aeropuerto : {}", id);
        Optional<Aeropuerto> aeropuerto = aeropuertoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aeropuerto);
    }

    /**
     * {@code DELETE  /aeropuertos/:id} : delete the "id" aeropuerto.
     *
     * @param id the id of the aeropuerto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aeropuertos/{id}")
    public ResponseEntity<Void> deleteAeropuerto(@PathVariable Long id) {
        log.debug("REST request to delete Aeropuerto : {}", id);
        aeropuertoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
