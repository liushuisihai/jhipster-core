package com.hshbic.skl.service.impl;

import com.hshbic.skl.service.SkillCategoryService;
import com.hshbic.skl.domain.SkillCategory;
import com.hshbic.skl.repository.SkillCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SkillCategory.
 */
@Service
@Transactional
public class SkillCategoryServiceImpl implements SkillCategoryService {

    private final Logger log = LoggerFactory.getLogger(SkillCategoryServiceImpl.class);

    private final SkillCategoryRepository skillCategoryRepository;

    public SkillCategoryServiceImpl(SkillCategoryRepository skillCategoryRepository) {
        this.skillCategoryRepository = skillCategoryRepository;
    }

    /**
     * Save a skillCategory.
     *
     * @param skillCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public SkillCategory save(SkillCategory skillCategory) {
        log.debug("Request to save SkillCategory : {}", skillCategory);
        return skillCategoryRepository.save(skillCategory);
    }

    /**
     * Get all the skillCategories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SkillCategory> findAll() {
        log.debug("Request to get all SkillCategories");
        return skillCategoryRepository.findAll();
    }


    /**
     * Get one skillCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillCategory> findOne(Long id) {
        log.debug("Request to get SkillCategory : {}", id);
        return skillCategoryRepository.findById(id);
    }

    /**
     * Delete the skillCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillCategory : {}", id);
        skillCategoryRepository.deleteById(id);
    }
}
