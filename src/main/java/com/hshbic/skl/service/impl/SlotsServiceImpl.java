package com.hshbic.skl.service.impl;

import com.hshbic.skl.service.SlotsService;
import com.hshbic.skl.domain.Slots;
import com.hshbic.skl.repository.SlotsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Slots.
 */
@Service
@Transactional
public class SlotsServiceImpl implements SlotsService {

    private final Logger log = LoggerFactory.getLogger(SlotsServiceImpl.class);

    private final SlotsRepository slotsRepository;

    public SlotsServiceImpl(SlotsRepository slotsRepository) {
        this.slotsRepository = slotsRepository;
    }

    /**
     * Save a slots.
     *
     * @param slots the entity to save
     * @return the persisted entity
     */
    @Override
    public Slots save(Slots slots) {
        log.debug("Request to save Slots : {}", slots);
        return slotsRepository.save(slots);
    }

    /**
     * Get all the slots.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Slots> findAll() {
        log.debug("Request to get all Slots");
        return slotsRepository.findAll();
    }


    /**
     * Get one slots by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Slots> findOne(Long id) {
        log.debug("Request to get Slots : {}", id);
        return slotsRepository.findById(id);
    }

    /**
     * Delete the slots by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Slots : {}", id);
        slotsRepository.deleteById(id);
    }
}
