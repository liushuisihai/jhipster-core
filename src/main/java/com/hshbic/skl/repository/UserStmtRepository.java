package com.hshbic.skl.repository;

import com.hshbic.skl.domain.UserStmt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserStmt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserStmtRepository extends JpaRepository<UserStmt, Long> {

}
