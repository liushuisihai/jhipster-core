package com.hshbic.skl.service;

import com.hshbic.skl.service.dto.SkillCategoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SkillCategory.
 */
public interface SkillCategoryService {

    /**
     * Save a skillCategory.
     *
     * @param skillCategoryDTO the entity to save
     * @return the persisted entity
     */
    SkillCategoryDTO save(SkillCategoryDTO skillCategoryDTO);

    /**
     * Get all the skillCategories.
     *
     * @return the list of entities
     */
    List<SkillCategoryDTO> findAll();


    /**
     * Get the "id" skillCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SkillCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" skillCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
