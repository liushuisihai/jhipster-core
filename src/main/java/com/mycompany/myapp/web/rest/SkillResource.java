package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Skill;
import com.mycompany.myapp.service.SkillService;
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
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/api")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    private static final String ENTITY_NAME = "myappSkill";

    private final SkillService skillService;

    public SkillResource(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * POST  /skills : Create a new skill.
     *
     * @param skill the skill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skill, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skills")
    @Timed
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            throw new BadRequestAlertException("A new skill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skill result = skillService.save(skill);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skills : Updates an existing skill.
     *
     * @param skill the skill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skill,
     * or with status 400 (Bad Request) if the skill is not valid,
     * or with status 500 (Internal Server Error) if the skill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skills")
    @Timed
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to update Skill : {}", skill);
        if (skill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Skill result = skillService.save(skill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skills : get all the skills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skills in body
     */
    @GetMapping("/skills")
    @Timed
    public List<Skill> getAllSkills() {
        log.debug("REST request to get all Skills");
        return skillService.findAll();
    }

    /**
     * GET  /skills/:id : get the "id" skill.
     *
     * @param id the id of the skill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skill, or with status 404 (Not Found)
     */
    @GetMapping("/skills/{id}")
    @Timed
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Optional<Skill> skill = skillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skill);
    }

    /**
     * DELETE  /skills/:id : delete the "id" skill.
     *
     * @param id the id of the skill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
