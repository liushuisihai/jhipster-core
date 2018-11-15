package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.UserStmtService;
import com.mycompany.myapp.domain.UserStmt;
import com.mycompany.myapp.repository.UserStmtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing UserStmt.
 */
@Service
@Transactional
public class UserStmtServiceImpl implements UserStmtService {

    private final Logger log = LoggerFactory.getLogger(UserStmtServiceImpl.class);

    private final UserStmtRepository userStmtRepository;

    public UserStmtServiceImpl(UserStmtRepository userStmtRepository) {
        this.userStmtRepository = userStmtRepository;
    }

    /**
     * Save a userStmt.
     *
     * @param userStmt the entity to save
     * @return the persisted entity
     */
    @Override
    public UserStmt save(UserStmt userStmt) {
        log.debug("Request to save UserStmt : {}", userStmt);
        return userStmtRepository.save(userStmt);
    }

    /**
     * Get all the userStmts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserStmt> findAll() {
        log.debug("Request to get all UserStmts");
        return userStmtRepository.findAll();
    }


    /**
     * Get one userStmt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserStmt> findOne(Long id) {
        log.debug("Request to get UserStmt : {}", id);
        return userStmtRepository.findById(id);
    }

    /**
     * Delete the userStmt by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserStmt : {}", id);
        userStmtRepository.deleteById(id);
    }
}
