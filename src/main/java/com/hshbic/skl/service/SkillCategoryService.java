package com.hshbic.skl.service;

import com.hshbic.skl.domain.SkillCategory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SkillCategory.
 */
public interface SkillCategoryService {

    /**
     * Save a skillCategory.
     *
     * @param skillCategory the entity to save
     * @return the persisted entity
     */
    SkillCategory save(SkillCategory skillCategory);

    /**
     * Get all the skillCategories.
     *
     * @return the list of entities
     */
    List<SkillCategory> findAll();


    /**
     * Get the "id" skillCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SkillCategory> findOne(Long id);

    /**
     * Delete the "id" skillCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
