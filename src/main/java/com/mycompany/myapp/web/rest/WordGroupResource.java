package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.WordGroup;
import com.mycompany.myapp.service.WordGroupService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing WordGroup.
 */
@RestController
@RequestMapping("/api")
public class WordGroupResource {

    private final Logger log = LoggerFactory.getLogger(WordGroupResource.class);

    private static final String ENTITY_NAME = "myappWordGroup";

    private final WordGroupService wordGroupService;

    public WordGroupResource(WordGroupService wordGroupService) {
        this.wordGroupService = wordGroupService;
    }

    /**
     * POST  /word-groups : Create a new wordGroup.
     *
     * @param wordGroup the wordGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wordGroup, or with status 400 (Bad Request) if the wordGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/word-groups")
    @Timed
    public ResponseEntity<WordGroup> createWordGroup(@Valid @RequestBody WordGroup wordGroup) throws URISyntaxException {
        log.debug("REST request to save WordGroup : {}", wordGroup);
        if (wordGroup.getId() != null) {
            throw new BadRequestAlertException("A new wordGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordGroup result = wordGroupService.save(wordGroup);
        return ResponseEntity.created(new URI("/api/word-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /word-groups : Updates an existing wordGroup.
     *
     * @param wordGroup the wordGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wordGroup,
     * or with status 400 (Bad Request) if the wordGroup is not valid,
     * or with status 500 (Internal Server Error) if the wordGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/word-groups")
    @Timed
    public ResponseEntity<WordGroup> updateWordGroup(@Valid @RequestBody WordGroup wordGroup) throws URISyntaxException {
        log.debug("REST request to update WordGroup : {}", wordGroup);
        if (wordGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordGroup result = wordGroupService.save(wordGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wordGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /word-groups : get all the wordGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of wordGroups in body
     */
    @GetMapping("/word-groups")
    @Timed
    public List<WordGroup> getAllWordGroups() {
        log.debug("REST request to get all WordGroups");
        return wordGroupService.findAll();
    }

    /**
     * GET  /word-groups/:id : get the "id" wordGroup.
     *
     * @param id the id of the wordGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wordGroup, or with status 404 (Not Found)
     */
    @GetMapping("/word-groups/{id}")
    @Timed
    public ResponseEntity<WordGroup> getWordGroup(@PathVariable Long id) {
        log.debug("REST request to get WordGroup : {}", id);
        Optional<WordGroup> wordGroup = wordGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wordGroup);
    }

    /**
     * DELETE  /word-groups/:id : delete the "id" wordGroup.
     *
     * @param id the id of the wordGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/word-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteWordGroup(@PathVariable Long id) {
        log.debug("REST request to delete WordGroup : {}", id);
        wordGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
