package com.crispico.absence_management.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.crispico.absence_management.domain.Berry;

import com.crispico.absence_management.repository.BerryRepository;
import com.crispico.absence_management.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Berry.
 */
@RestController
@RequestMapping("/api")
public class BerryResource {

    private final Logger log = LoggerFactory.getLogger(BerryResource.class);

    private static final String ENTITY_NAME = "berry";

    private final BerryRepository berryRepository;

    public BerryResource(BerryRepository berryRepository) {
        this.berryRepository = berryRepository;
    }

    /**
     * POST  /berries : Create a new berry.
     *
     * @param berry the berry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new berry, or with status 400 (Bad Request) if the berry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/berries")
    @Timed
    public ResponseEntity<Berry> createBerry(@Valid @RequestBody Berry berry) throws URISyntaxException {
        log.debug("REST request to save Berry : {}", berry);
        if (berry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new berry cannot already have an ID")).body(null);
        }
        Berry result = berryRepository.save(berry);
        return ResponseEntity.created(new URI("/api/berries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /berries : Updates an existing berry.
     *
     * @param berry the berry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated berry,
     * or with status 400 (Bad Request) if the berry is not valid,
     * or with status 500 (Internal Server Error) if the berry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/berries")
    @Timed
    public ResponseEntity<Berry> updateBerry(@Valid @RequestBody Berry berry) throws URISyntaxException {
        log.debug("REST request to update Berry : {}", berry);
        if (berry.getId() == null) {
            return createBerry(berry);
        }
        Berry result = berryRepository.save(berry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, berry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /berries : get all the berries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of berries in body
     */
    @GetMapping("/berries")
    @Timed
    public List<Berry> getAllBerries() {
        log.debug("REST request to get all Berries");
        return berryRepository.findAll();
    }

    /**
     * GET  /berries/:id : get the "id" berry.
     *
     * @param id the id of the berry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the berry, or with status 404 (Not Found)
     */
    @GetMapping("/berries/{id}")
    @Timed
    public ResponseEntity<Berry> getBerry(@PathVariable Long id) {
        log.debug("REST request to get Berry : {}", id);
        Berry berry = berryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(berry));
    }

    /**
     * DELETE  /berries/:id : delete the "id" berry.
     *
     * @param id the id of the berry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/berries/{id}")
    @Timed
    public ResponseEntity<Void> deleteBerry(@PathVariable Long id) {
        log.debug("REST request to delete Berry : {}", id);
        berryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
