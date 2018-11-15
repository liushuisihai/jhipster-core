package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Intent;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Intent.
 */
public interface IntentService {

    /**
     * Save a intent.
     *
     * @param intent the entity to save
     * @return the persisted entity
     */
    Intent save(Intent intent);

    /**
     * Get all the intents.
     *
     * @return the list of entities
     */
    List<Intent> findAll();


    /**
     * Get the "id" intent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Intent> findOne(Long id);

    /**
     * Delete the "id" intent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
