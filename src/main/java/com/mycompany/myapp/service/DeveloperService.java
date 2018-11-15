package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Developer;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Developer.
 */
public interface DeveloperService {

    /**
     * Save a developer.
     *
     * @param developer the entity to save
     * @return the persisted entity
     */
    Developer save(Developer developer);

    /**
     * Get all the developers.
     *
     * @return the list of entities
     */
    List<Developer> findAll();


    /**
     * Get the "id" developer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Developer> findOne(Long id);

    /**
     * Delete the "id" developer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
