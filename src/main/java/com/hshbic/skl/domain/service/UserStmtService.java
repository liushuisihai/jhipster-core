package com.hshbic.skl.domain.service;

import com.hshbic.skl.domain.domain.UserStmt;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserStmt.
 */
public interface UserStmtService {

    /**
     * Save a userStmt.
     *
     * @param userStmt the entity to save
     * @return the persisted entity
     */
    UserStmt save(UserStmt userStmt);

    /**
     * Get all the userStmts.
     *
     * @return the list of entities
     */
    List<UserStmt> findAll();


    /**
     * Get the "id" userStmt.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserStmt> findOne(Long id);

    /**
     * Delete the "id" userStmt.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
