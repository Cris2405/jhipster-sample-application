package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Vuelo;
import com.mycompany.myapp.repository.VueloRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Vuelo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VueloResource {

    private final Logger log = LoggerFactory.getLogger(VueloResource.class);

    private static final String ENTITY_NAME = "vuelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VueloRepository vueloRepository;

    public VueloResource(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    /**
     * {@code POST  /vuelos} : Create a new vuelo.
     *
     * @param vuelo the vuelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vuelo, or with status {@code 400 (Bad Request)} if the vuelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vuelos")
    public ResponseEntity<Vuelo> createVuelo(@RequestBody Vuelo vuelo) throws URISyntaxException {
        log.debug("REST request to save Vuelo : {}", vuelo);
        if (vuelo.getId() != null) {
            throw new BadRequestAlertException("A new vuelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vuelo result = vueloRepository.save(vuelo);
        return ResponseEntity.created(new URI("/api/vuelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vuelos} : Updates an existing vuelo.
     *
     * @param vuelo the vuelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vuelo,
     * or with status {@code 400 (Bad Request)} if the vuelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vuelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vuelos")
    public ResponseEntity<Vuelo> updateVuelo(@RequestBody Vuelo vuelo) throws URISyntaxException {
        log.debug("REST request to update Vuelo : {}", vuelo);
        if (vuelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vuelo result = vueloRepository.save(vuelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vuelo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vuelos} : get all the vuelos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vuelos in body.
     */
    @GetMapping("/vuelos")
    public List<Vuelo> getAllVuelos() {
        log.debug("REST request to get all Vuelos");
        return vueloRepository.findAll();
    }

    /**
     * {@code GET  /vuelos/:id} : get the "id" vuelo.
     *
     * @param id the id of the vuelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vuelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vuelos/{id}")
    public ResponseEntity<Vuelo> getVuelo(@PathVariable Long id) {
        log.debug("REST request to get Vuelo : {}", id);
        Optional<Vuelo> vuelo = vueloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vuelo);
    }

    /**
     * {@code DELETE  /vuelos/:id} : delete the "id" vuelo.
     *
     * @param id the id of the vuelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vuelos/{id}")
    public ResponseEntity<Void> deleteVuelo(@PathVariable Long id) {
        log.debug("REST request to delete Vuelo : {}", id);
        vueloRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
