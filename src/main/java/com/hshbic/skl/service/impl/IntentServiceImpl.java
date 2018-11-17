package com.hshbic.skl.service.impl;

import com.hshbic.skl.service.IntentService;
import com.hshbic.skl.domain.Intent;
import com.hshbic.skl.repository.IntentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Intent.
 */
@Service
@Transactional
public class IntentServiceImpl implements IntentService {

    private final Logger log = LoggerFactory.getLogger(IntentServiceImpl.class);

    private final IntentRepository intentRepository;

    public IntentServiceImpl(IntentRepository intentRepository) {
        this.intentRepository = intentRepository;
    }

    /**
     * Save a intent.
     *
     * @param intent the entity to save
     * @return the persisted entity
     */
    @Override
    public Intent save(Intent intent) {
        log.debug("Request to save Intent : {}", intent);
        return intentRepository.save(intent);
    }

    /**
     * Get all the intents.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Intent> findAll() {
        log.debug("Request to get all Intents");
        return intentRepository.findAll();
    }


    /**
     * Get one intent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Intent> findOne(Long id) {
        log.debug("Request to get Intent : {}", id);
        return intentRepository.findById(id);
    }

    /**
     * Delete the intent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intent : {}", id);
        intentRepository.deleteById(id);
    }
}
