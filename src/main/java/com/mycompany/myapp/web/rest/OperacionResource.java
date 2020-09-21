package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Operacion;
import com.mycompany.myapp.repository.OperacionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Operacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OperacionResource {

    private final Logger log = LoggerFactory.getLogger(OperacionResource.class);

    private static final String ENTITY_NAME = "operacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperacionRepository operacionRepository;

    public OperacionResource(OperacionRepository operacionRepository) {
        this.operacionRepository = operacionRepository;
    }

    /**
     * {@code POST  /operacions} : Create a new operacion.
     *
     * @param operacion the operacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operacion, or with status {@code 400 (Bad Request)} if the operacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operacions")
    public ResponseEntity<Operacion> createOperacion(@RequestBody Operacion operacion) throws URISyntaxException {
        log.debug("REST request to save Operacion : {}", operacion);
        if (operacion.getId() != null) {
            throw new BadRequestAlertException("A new operacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Operacion result = operacionRepository.save(operacion);
        return ResponseEntity.created(new URI("/api/operacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operacions} : Updates an existing operacion.
     *
     * @param operacion the operacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operacion,
     * or with status {@code 400 (Bad Request)} if the operacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operacions")
    public ResponseEntity<Operacion> updateOperacion(@RequestBody Operacion operacion) throws URISyntaxException {
        log.debug("REST request to update Operacion : {}", operacion);
        if (operacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Operacion result = operacionRepository.save(operacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /operacions} : get all the operacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operacions in body.
     */
    @GetMapping("/operacions")
    public List<Operacion> getAllOperacions() {
        log.debug("REST request to get all Operacions");
        return operacionRepository.findAll();
    }

    /**
     * {@code GET  /operacions/:id} : get the "id" operacion.
     *
     * @param id the id of the operacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operacions/{id}")
    public ResponseEntity<Operacion> getOperacion(@PathVariable Long id) {
        log.debug("REST request to get Operacion : {}", id);
        Optional<Operacion> operacion = operacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(operacion);
    }

    /**
     * {@code DELETE  /operacions/:id} : delete the "id" operacion.
     *
     * @param id the id of the operacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operacions/{id}")
    public ResponseEntity<Void> deleteOperacion(@PathVariable Long id) {
        log.debug("REST request to delete Operacion : {}", id);
        operacionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
