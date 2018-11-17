package com.hshbic.skl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hshbic.skl.service.SkillCategoryService;
import com.hshbic.skl.web.rest.errors.BadRequestAlertException;
import com.hshbic.skl.web.rest.util.HeaderUtil;
import com.hshbic.skl.service.dto.SkillCategoryDTO;
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
 * REST controller for managing SkillCategory.
 */
@RestController
@RequestMapping("/api")
public class SkillCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SkillCategoryResource.class);

    private static final String ENTITY_NAME = "myappSkillCategory";

    private final SkillCategoryService skillCategoryService;

    public SkillCategoryResource(SkillCategoryService skillCategoryService) {
        this.skillCategoryService = skillCategoryService;
    }

    /**
     * POST  /skill-categories : Create a new skillCategory.
     *
     * @param skillCategoryDTO the skillCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillCategoryDTO, or with status 400 (Bad Request) if the skillCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skill-categories")
    @Timed
    public ResponseEntity<SkillCategoryDTO> createSkillCategory(@Valid @RequestBody SkillCategoryDTO skillCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save SkillCategory : {}", skillCategoryDTO);
        if (skillCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new skillCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillCategoryDTO result = skillCategoryService.save(skillCategoryDTO);
        return ResponseEntity.created(new URI("/api/skill-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skill-categories : Updates an existing skillCategory.
     *
     * @param skillCategoryDTO the skillCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillCategoryDTO,
     * or with status 400 (Bad Request) if the skillCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the skillCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skill-categories")
    @Timed
    public ResponseEntity<SkillCategoryDTO> updateSkillCategory(@Valid @RequestBody SkillCategoryDTO skillCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update SkillCategory : {}", skillCategoryDTO);
        if (skillCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillCategoryDTO result = skillCategoryService.save(skillCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skill-categories : get all the skillCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skillCategories in body
     */
    @GetMapping("/skill-categories")
    @Timed
    public List<SkillCategoryDTO> getAllSkillCategories() {
        log.debug("REST request to get all SkillCategories");
        return skillCategoryService.findAll();
    }

    /**
     * GET  /skill-categories/:id : get the "id" skillCategory.
     *
     * @param id the id of the skillCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/skill-categories/{id}")
    @Timed
    public ResponseEntity<SkillCategoryDTO> getSkillCategory(@PathVariable Long id) {
        log.debug("REST request to get SkillCategory : {}", id);
        Optional<SkillCategoryDTO> skillCategoryDTO = skillCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillCategoryDTO);
    }

    /**
     * DELETE  /skill-categories/:id : delete the "id" skillCategory.
     *
     * @param id the id of the skillCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skill-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillCategory(@PathVariable Long id) {
        log.debug("REST request to delete SkillCategory : {}", id);
        skillCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}