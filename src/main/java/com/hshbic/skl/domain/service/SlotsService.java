package com.hshbic.skl.domain.service;

import com.hshbic.skl.domain.domain.Slots;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Slots.
 */
public interface SlotsService {

    /**
     * Save a slots.
     *
     * @param slots the entity to save
     * @return the persisted entity
     */
    Slots save(Slots slots);

    /**
     * Get all the slots.
     *
     * @return the list of entities
     */
    List<Slots> findAll();


    /**
     * Get the "id" slots.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Slots> findOne(Long id);

    /**
     * Delete the "id" slots.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
