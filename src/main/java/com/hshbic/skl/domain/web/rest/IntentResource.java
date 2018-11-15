package com.hshbic.skl.domain.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hshbic.skl.domain.domain.Intent;
import com.hshbic.skl.domain.service.IntentService;
import com.hshbic.skl.domain.web.rest.errors.BadRequestAlertException;
import com.hshbic.skl.domain.web.rest.util.HeaderUtil;
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
 * REST controller for managing Intent.
 */
@RestController
@RequestMapping("/api")
public class IntentResource {

    private final Logger log = LoggerFactory.getLogger(IntentResource.class);

    private static final String ENTITY_NAME = "myappIntent";

    private final IntentService intentService;

    public IntentResource(IntentService intentService) {
        this.intentService = intentService;
    }

    /**
     * POST  /intents : Create a new intent.
     *
     * @param intent the intent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new intent, or with status 400 (Bad Request) if the intent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/intents")
    @Timed
    public ResponseEntity<Intent> createIntent(@Valid @RequestBody Intent intent) throws URISyntaxException {
        log.debug("REST request to save Intent : {}", intent);
        if (intent.getId() != null) {
            throw new BadRequestAlertException("A new intent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Intent result = intentService.save(intent);
        return ResponseEntity.created(new URI("/api/intents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /intents : Updates an existing intent.
     *
     * @param intent the intent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated intent,
     * or with status 400 (Bad Request) if the intent is not valid,
     * or with status 500 (Internal Server Error) if the intent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/intents")
    @Timed
    public ResponseEntity<Intent> updateIntent(@Valid @RequestBody Intent intent) throws URISyntaxException {
        log.debug("REST request to update Intent : {}", intent);
        if (intent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Intent result = intentService.save(intent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, intent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /intents : get all the intents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of intents in body
     */
    @GetMapping("/intents")
    @Timed
    public List<Intent> getAllIntents() {
        log.debug("REST request to get all Intents");
        return intentService.findAll();
    }

    /**
     * GET  /intents/:id : get the "id" intent.
     *
     * @param id the id of the intent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the intent, or with status 404 (Not Found)
     */
    @GetMapping("/intents/{id}")
    @Timed
    public ResponseEntity<Intent> getIntent(@PathVariable Long id) {
        log.debug("REST request to get Intent : {}", id);
        Optional<Intent> intent = intentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intent);
    }

    /**
     * DELETE  /intents/:id : delete the "id" intent.
     *
     * @param id the id of the intent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/intents/{id}")
    @Timed
    public ResponseEntity<Void> deleteIntent(@PathVariable Long id) {
        log.debug("REST request to delete Intent : {}", id);
        intentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
