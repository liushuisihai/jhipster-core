package com.hshbic.skl.service.impl;

import com.hshbic.skl.service.SkillCategoryService;
import com.hshbic.skl.domain.SkillCategory;
import com.hshbic.skl.repository.SkillCategoryRepository;
import com.hshbic.skl.service.dto.SkillCategoryDTO;
import com.hshbic.skl.service.mapper.SkillCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SkillCategory.
 */
@Service
@Transactional
public class SkillCategoryServiceImpl implements SkillCategoryService {

    private final Logger log = LoggerFactory.getLogger(SkillCategoryServiceImpl.class);

    private final SkillCategoryRepository skillCategoryRepository;

    private final SkillCategoryMapper skillCategoryMapper;

    public SkillCategoryServiceImpl(SkillCategoryRepository skillCategoryRepository, SkillCategoryMapper skillCategoryMapper) {
        this.skillCategoryRepository = skillCategoryRepository;
        this.skillCategoryMapper = skillCategoryMapper;
    }

    /**
     * Save a skillCategory.
     *
     * @param skillCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SkillCategoryDTO save(SkillCategoryDTO skillCategoryDTO) {
        log.debug("Request to save SkillCategory : {}", skillCategoryDTO);

        SkillCategory skillCategory = skillCategoryMapper.toEntity(skillCategoryDTO);
        skillCategory = skillCategoryRepository.save(skillCategory);
        return skillCategoryMapper.toDto(skillCategory);
    }

    /**
     * Get all the skillCategories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SkillCategoryDTO> findAll() {
        log.debug("Request to get all SkillCategories");
        return skillCategoryRepository.findAll().stream()
            .map(skillCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one skillCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillCategoryDTO> findOne(Long id) {
        log.debug("Request to get SkillCategory : {}", id);
        return skillCategoryRepository.findById(id)
            .map(skillCategoryMapper::toDto);
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
