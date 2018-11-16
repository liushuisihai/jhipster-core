package com.hshbic.skl.service.impl;

import com.hshbic.skl.service.DictionaryService;
import com.hshbic.skl.domain.Dictionary;
import com.hshbic.skl.repository.DictionaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Dictionary.
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    private final Logger log = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    private final DictionaryRepository dictionaryRepository;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    /**
     * Save a dictionary.
     *
     * @param dictionary the entity to save
     * @return the persisted entity
     */
    @Override
    public Dictionary save(Dictionary dictionary) {
        log.debug("Request to save Dictionary : {}", dictionary);
        return dictionaryRepository.save(dictionary);
    }

    /**
     * Get all the dictionaries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Dictionary> findAll() {
        log.debug("Request to get all Dictionaries");
        return dictionaryRepository.findAll();
    }


    /**
     * Get one dictionary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Dictionary> findOne(Long id) {
        log.debug("Request to get Dictionary : {}", id);
        return dictionaryRepository.findById(id);
    }

    /**
     * Delete the dictionary by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dictionary : {}", id);
        dictionaryRepository.deleteById(id);
    }
}
