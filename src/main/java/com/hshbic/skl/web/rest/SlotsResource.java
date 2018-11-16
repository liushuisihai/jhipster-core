package com.hshbic.skl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hshbic.skl.domain.Slots;
import com.hshbic.skl.service.SlotsService;
import com.hshbic.skl.web.rest.errors.BadRequestAlertException;
import com.hshbic.skl.web.rest.util.HeaderUtil;
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
 * REST controller for managing Slots.
 */
@RestController
@RequestMapping("/api")
public class SlotsResource {

    private final Logger log = LoggerFactory.getLogger(SlotsResource.class);

    private static final String ENTITY_NAME = "myappSlots";

    private final SlotsService slotsService;

    public SlotsResource(SlotsService slotsService) {
        this.slotsService = slotsService;
    }

    /**
     * POST  /slots : Create a new slots.
     *
     * @param slots the slots to create
     * @return the ResponseEntity with status 201 (Created) and with body the new slots, or with status 400 (Bad Request) if the slots has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/slots")
    @Timed
    public ResponseEntity<Slots> createSlots(@Valid @RequestBody Slots slots) throws URISyntaxException {
        log.debug("REST request to save Slots : {}", slots);
        if (slots.getId() != null) {
            throw new BadRequestAlertException("A new slots cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Slots result = slotsService.save(slots);
        return ResponseEntity.created(new URI("/api/slots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /slots : Updates an existing slots.
     *
     * @param slots the slots to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated slots,
     * or with status 400 (Bad Request) if the slots is not valid,
     * or with status 500 (Internal Server Error) if the slots couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/slots")
    @Timed
    public ResponseEntity<Slots> updateSlots(@Valid @RequestBody Slots slots) throws URISyntaxException {
        log.debug("REST request to update Slots : {}", slots);
        if (slots.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Slots result = slotsService.save(slots);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, slots.getId().toString()))
            .body(result);
    }

    /**
     * GET  /slots : get all the slots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of slots in body
     */
    @GetMapping("/slots")
    @Timed
    public List<Slots> getAllSlots() {
        log.debug("REST request to get all Slots");
        return slotsService.findAll();
    }

    /**
     * GET  /slots/:id : get the "id" slots.
     *
     * @param id the id of the slots to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the slots, or with status 404 (Not Found)
     */
    @GetMapping("/slots/{id}")
    @Timed
    public ResponseEntity<Slots> getSlots(@PathVariable Long id) {
        log.debug("REST request to get Slots : {}", id);
        Optional<Slots> slots = slotsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(slots);
    }

    /**
     * DELETE  /slots/:id : delete the "id" slots.
     *
     * @param id the id of the slots to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/slots/{id}")
    @Timed
    public ResponseEntity<Void> deleteSlots(@PathVariable Long id) {
        log.debug("REST request to delete Slots : {}", id);
        slotsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
