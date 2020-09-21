package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Pasajero;
import com.mycompany.myapp.repository.PasajeroRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Pasajero}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PasajeroResource {

    private final Logger log = LoggerFactory.getLogger(PasajeroResource.class);

    private static final String ENTITY_NAME = "pasajero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PasajeroRepository pasajeroRepository;

    public PasajeroResource(PasajeroRepository pasajeroRepository) {
        this.pasajeroRepository = pasajeroRepository;
    }

    /**
     * {@code POST  /pasajeros} : Create a new pasajero.
     *
     * @param pasajero the pasajero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pasajero, or with status {@code 400 (Bad Request)} if the pasajero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pasajeros")
    public ResponseEntity<Pasajero> createPasajero(@RequestBody Pasajero pasajero) throws URISyntaxException {
        log.debug("REST request to save Pasajero : {}", pasajero);
        if (pasajero.getId() != null) {
            throw new BadRequestAlertException("A new pasajero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pasajero result = pasajeroRepository.save(pasajero);
        return ResponseEntity.created(new URI("/api/pasajeros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pasajeros} : Updates an existing pasajero.
     *
     * @param pasajero the pasajero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pasajero,
     * or with status {@code 400 (Bad Request)} if the pasajero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pasajero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pasajeros")
    public ResponseEntity<Pasajero> updatePasajero(@RequestBody Pasajero pasajero) throws URISyntaxException {
        log.debug("REST request to update Pasajero : {}", pasajero);
        if (pasajero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pasajero result = pasajeroRepository.save(pasajero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pasajero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pasajeros} : get all the pasajeros.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pasajeros in body.
     */
    @GetMapping("/pasajeros")
    public List<Pasajero> getAllPasajeros(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Pasajeros");
        return pasajeroRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /pasajeros/:id} : get the "id" pasajero.
     *
     * @param id the id of the pasajero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pasajero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pasajeros/{id}")
    public ResponseEntity<Pasajero> getPasajero(@PathVariable Long id) {
        log.debug("REST request to get Pasajero : {}", id);
        Optional<Pasajero> pasajero = pasajeroRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(pasajero);
    }

    /**
     * {@code DELETE  /pasajeros/:id} : delete the "id" pasajero.
     *
     * @param id the id of the pasajero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pasajeros/{id}")
    public ResponseEntity<Void> deletePasajero(@PathVariable Long id) {
        log.debug("REST request to delete Pasajero : {}", id);
        pasajeroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
