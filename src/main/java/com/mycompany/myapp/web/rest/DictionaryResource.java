package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Dictionary;
import com.mycompany.myapp.service.DictionaryService;
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
 * REST controller for managing Dictionary.
 */
@RestController
@RequestMapping("/api")
public class DictionaryResource {

    private final Logger log = LoggerFactory.getLogger(DictionaryResource.class);

    private static final String ENTITY_NAME = "myappDictionary";

    private final DictionaryService dictionaryService;

    public DictionaryResource(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * POST  /dictionaries : Create a new dictionary.
     *
     * @param dictionary the dictionary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dictionary, or with status 400 (Bad Request) if the dictionary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dictionaries")
    @Timed
    public ResponseEntity<Dictionary> createDictionary(@Valid @RequestBody Dictionary dictionary) throws URISyntaxException {
        log.debug("REST request to save Dictionary : {}", dictionary);
        if (dictionary.getId() != null) {
            throw new BadRequestAlertException("A new dictionary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dictionary result = dictionaryService.save(dictionary);
        return ResponseEntity.created(new URI("/api/dictionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dictionaries : Updates an existing dictionary.
     *
     * @param dictionary the dictionary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dictionary,
     * or with status 400 (Bad Request) if the dictionary is not valid,
     * or with status 500 (Internal Server Error) if the dictionary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dictionaries")
    @Timed
    public ResponseEntity<Dictionary> updateDictionary(@Valid @RequestBody Dictionary dictionary) throws URISyntaxException {
        log.debug("REST request to update Dictionary : {}", dictionary);
        if (dictionary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dictionary result = dictionaryService.save(dictionary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dictionary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dictionaries : get all the dictionaries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dictionaries in body
     */
    @GetMapping("/dictionaries")
    @Timed
    public List<Dictionary> getAllDictionaries() {
        log.debug("REST request to get all Dictionaries");
        return dictionaryService.findAll();
    }

    /**
     * GET  /dictionaries/:id : get the "id" dictionary.
     *
     * @param id the id of the dictionary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dictionary, or with status 404 (Not Found)
     */
    @GetMapping("/dictionaries/{id}")
    @Timed
    public ResponseEntity<Dictionary> getDictionary(@PathVariable Long id) {
        log.debug("REST request to get Dictionary : {}", id);
        Optional<Dictionary> dictionary = dictionaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dictionary);
    }

    /**
     * DELETE  /dictionaries/:id : delete the "id" dictionary.
     *
     * @param id the id of the dictionary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dictionaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDictionary(@PathVariable Long id) {
        log.debug("REST request to delete Dictionary : {}", id);
        dictionaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
