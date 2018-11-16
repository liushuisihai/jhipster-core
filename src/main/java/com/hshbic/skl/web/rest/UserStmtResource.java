package com.hshbic.skl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hshbic.skl.domain.UserStmt;
import com.hshbic.skl.service.UserStmtService;
import com.hshbic.skl.web.rest.errors.BadRequestAlertException;
import com.hshbic.skl.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserStmt.
 */
@RestController
@RequestMapping("/api")
public class UserStmtResource {

    private final Logger log = LoggerFactory.getLogger(UserStmtResource.class);

    private static final String ENTITY_NAME = "myappUserStmt";

    private final UserStmtService userStmtService;

    public UserStmtResource(UserStmtService userStmtService) {
        this.userStmtService = userStmtService;
    }

    /**
     * POST  /user-stmts : Create a new userStmt.
     *
     * @param userStmt the userStmt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userStmt, or with status 400 (Bad Request) if the userStmt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-stmts")
    @Timed
    public ResponseEntity<UserStmt> createUserStmt(@Valid @RequestBody UserStmt userStmt) throws URISyntaxException {
        log.debug("REST request to save UserStmt : {}", userStmt);
        if (userStmt.getId() != null) {
            throw new BadRequestAlertException("A new userStmt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserStmt result = userStmtService.save(userStmt);
        return ResponseEntity.created(new URI("/api/user-stmts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-stmts : Updates an existing userStmt.
     *
     * @param userStmt the userStmt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userStmt,
     * or with status 400 (Bad Request) if the userStmt is not valid,
     * or with status 500 (Internal Server Error) if the userStmt couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-stmts")
    @Timed
    public ResponseEntity<UserStmt> updateUserStmt(@Valid @RequestBody UserStmt userStmt) throws URISyntaxException {
        log.debug("REST request to update UserStmt : {}", userStmt);
        if (userStmt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserStmt result = userStmtService.save(userStmt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userStmt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-stmts : get all the userStmts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userStmts in body
     */
    @GetMapping("/user-stmts")
    @Timed
    public List<UserStmt> getAllUserStmts() {
        log.debug("REST request to get all UserStmts");
        return userStmtService.findAll();
    }

    /**
     * GET  /user-stmts/:id : get the "id" userStmt.
     *
     * @param id the id of the userStmt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userStmt, or with status 404 (Not Found)
     */
    @GetMapping("/user-stmts/{id}")
    @Timed
    public ResponseEntity<UserStmt> getUserStmt(@PathVariable Long id) {
        log.debug("REST request to get UserStmt : {}", id);
        Optional<UserStmt> userStmt = userStmtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userStmt);
    }

    /**
     * DELETE  /user-stmts/:id : delete the "id" userStmt.
     *
     * @param id the id of the userStmt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-stmts/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserStmt(@PathVariable Long id) {
        log.debug("REST request to delete UserStmt : {}", id);
        userStmtService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
