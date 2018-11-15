package com.hshbic.skl.service.impl;

import com.hshbic.skl.service.SkillService;
import com.hshbic.skl.domain.Skill;
import com.hshbic.skl.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Skill.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * Save a skill.
     *
     * @param skill the entity to save
     * @return the persisted entity
     */
    @Override
    public Skill save(Skill skill) {
        log.debug("Request to save Skill : {}", skill);
        return skillRepository.save(skill);
    }

    /**
     * Get all the skills.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        log.debug("Request to get all Skills");
        return skillRepository.findAll();
    }


    /**
     * Get one skill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Skill> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id);
    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }
}
