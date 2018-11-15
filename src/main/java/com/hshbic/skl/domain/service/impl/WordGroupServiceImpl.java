package com.hshbic.skl.domain.service.impl;

import com.hshbic.skl.domain.service.WordGroupService;
import com.hshbic.skl.domain.domain.WordGroup;
import com.hshbic.skl.domain.repository.WordGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing WordGroup.
 */
@Service
@Transactional
public class WordGroupServiceImpl implements WordGroupService {

    private final Logger log = LoggerFactory.getLogger(WordGroupServiceImpl.class);

    private final WordGroupRepository wordGroupRepository;

    public WordGroupServiceImpl(WordGroupRepository wordGroupRepository) {
        this.wordGroupRepository = wordGroupRepository;
    }

    /**
     * Save a wordGroup.
     *
     * @param wordGroup the entity to save
     * @return the persisted entity
     */
    @Override
    public WordGroup save(WordGroup wordGroup) {
        log.debug("Request to save WordGroup : {}", wordGroup);
        return wordGroupRepository.save(wordGroup);
    }

    /**
     * Get all the wordGroups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WordGroup> findAll() {
        log.debug("Request to get all WordGroups");
        return wordGroupRepository.findAll();
    }


    /**
     * Get one wordGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WordGroup> findOne(Long id) {
        log.debug("Request to get WordGroup : {}", id);
        return wordGroupRepository.findById(id);
    }

    /**
     * Delete the wordGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WordGroup : {}", id);
        wordGroupRepository.deleteById(id);
    }
}
