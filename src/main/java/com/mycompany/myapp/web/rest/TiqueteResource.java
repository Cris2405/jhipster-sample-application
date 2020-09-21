package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Tiquete;
import com.mycompany.myapp.repository.TiqueteRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Tiquete}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TiqueteResource {

    private final Logger log = LoggerFactory.getLogger(TiqueteResource.class);

    private static final String ENTITY_NAME = "tiquete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TiqueteRepository tiqueteRepository;

    public TiqueteResource(TiqueteRepository tiqueteRepository) {
        this.tiqueteRepository = tiqueteRepository;
    }

    /**
     * {@code POST  /tiquetes} : Create a new tiquete.
     *
     * @param tiquete the tiquete to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tiquete, or with status {@code 400 (Bad Request)} if the tiquete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tiquetes")
    public ResponseEntity<Tiquete> createTiquete(@RequestBody Tiquete tiquete) throws URISyntaxException {
        log.debug("REST request to save Tiquete : {}", tiquete);
        if (tiquete.getId() != null) {
            throw new BadRequestAlertException("A new tiquete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tiquete result = tiqueteRepository.save(tiquete);
        return ResponseEntity.created(new URI("/api/tiquetes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tiquetes} : Updates an existing tiquete.
     *
     * @param tiquete the tiquete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tiquete,
     * or with status {@code 400 (Bad Request)} if the tiquete is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tiquete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tiquetes")
    public ResponseEntity<Tiquete> updateTiquete(@RequestBody Tiquete tiquete) throws URISyntaxException {
        log.debug("REST request to update Tiquete : {}", tiquete);
        if (tiquete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tiquete result = tiqueteRepository.save(tiquete);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tiquete.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tiquetes} : get all the tiquetes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tiquetes in body.
     */
    @GetMapping("/tiquetes")
    public List<Tiquete> getAllTiquetes() {
        log.debug("REST request to get all Tiquetes");
        return tiqueteRepository.findAll();
    }

    /**
     * {@code GET  /tiquetes/:id} : get the "id" tiquete.
     *
     * @param id the id of the tiquete to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tiquete, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tiquetes/{id}")
    public ResponseEntity<Tiquete> getTiquete(@PathVariable Long id) {
        log.debug("REST request to get Tiquete : {}", id);
        Optional<Tiquete> tiquete = tiqueteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tiquete);
    }

    /**
     * {@code DELETE  /tiquetes/:id} : delete the "id" tiquete.
     *
     * @param id the id of the tiquete to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tiquetes/{id}")
    public ResponseEntity<Void> deleteTiquete(@PathVariable Long id) {
        log.debug("REST request to delete Tiquete : {}", id);
        tiqueteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
