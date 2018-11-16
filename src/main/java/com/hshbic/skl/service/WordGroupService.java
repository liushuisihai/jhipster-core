package com.hshbic.skl.service;

import com.hshbic.skl.domain.WordGroup;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing WordGroup.
 */
public interface WordGroupService {

    /**
     * Save a wordGroup.
     *
     * @param wordGroup the entity to save
     * @return the persisted entity
     */
    WordGroup save(WordGroup wordGroup);

    /**
     * Get all the wordGroups.
     *
     * @return the list of entities
     */
    List<WordGroup> findAll();


    /**
     * Get the "id" wordGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WordGroup> findOne(Long id);

    /**
     * Delete the "id" wordGroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
