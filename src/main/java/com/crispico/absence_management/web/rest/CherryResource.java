package com.crispico.absence_management.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.crispico.absence_management.domain.Cherry;

import com.crispico.absence_management.repository.CherryRepository;
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
 * REST controller for managing Cherry.
 */
@RestController
@RequestMapping("/api")
public class CherryResource {

    private final Logger log = LoggerFactory.getLogger(CherryResource.class);

    private static final String ENTITY_NAME = "cherry";

    private final CherryRepository cherryRepository;

    public CherryResource(CherryRepository cherryRepository) {
        this.cherryRepository = cherryRepository;
    }

    /**
     * POST  /cherries : Create a new cherry.
     *
     * @param cherry the cherry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cherry, or with status 400 (Bad Request) if the cherry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cherries")
    @Timed
    public ResponseEntity<Cherry> createCherry(@Valid @RequestBody Cherry cherry) throws URISyntaxException {
        log.debug("REST request to save Cherry : {}", cherry);
        if (cherry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cherry cannot already have an ID")).body(null);
        }
        Cherry result = cherryRepository.save(cherry);
        return ResponseEntity.created(new URI("/api/cherries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cherries : Updates an existing cherry.
     *
     * @param cherry the cherry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cherry,
     * or with status 400 (Bad Request) if the cherry is not valid,
     * or with status 500 (Internal Server Error) if the cherry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cherries")
    @Timed
    public ResponseEntity<Cherry> updateCherry(@Valid @RequestBody Cherry cherry) throws URISyntaxException {
        log.debug("REST request to update Cherry : {}", cherry);
        if (cherry.getId() == null) {
            return createCherry(cherry);
        }
        Cherry result = cherryRepository.save(cherry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cherry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cherries : get all the cherries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cherries in body
     */
    @GetMapping("/cherries")
    @Timed
    public List<Cherry> getAllCherries() {
        log.debug("REST request to get all Cherries");
        return cherryRepository.findAll();
    }

    /**
     * GET  /cherries/:id : get the "id" cherry.
     *
     * @param id the id of the cherry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cherry, or with status 404 (Not Found)
     */
    @GetMapping("/cherries/{id}")
    @Timed
    public ResponseEntity<Cherry> getCherry(@PathVariable Long id) {
        log.debug("REST request to get Cherry : {}", id);
        Cherry cherry = cherryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cherry));
    }

    /**
     * DELETE  /cherries/:id : delete the "id" cherry.
     *
     * @param id the id of the cherry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cherries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCherry(@PathVariable Long id) {
        log.debug("REST request to delete Cherry : {}", id);
        cherryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
