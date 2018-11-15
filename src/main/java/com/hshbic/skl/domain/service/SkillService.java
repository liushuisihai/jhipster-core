package com.hshbic.skl.domain.service;

import com.hshbic.skl.domain.domain.Skill;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Skill.
 */
public interface SkillService {

    /**
     * Save a skill.
     *
     * @param skill the entity to save
     * @return the persisted entity
     */
    Skill save(Skill skill);

    /**
     * Get all the skills.
     *
     * @return the list of entities
     */
    List<Skill> findAll();


    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Skill> findOne(Long id);

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
