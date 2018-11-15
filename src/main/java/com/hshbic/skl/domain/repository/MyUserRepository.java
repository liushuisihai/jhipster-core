package com.hshbic.skl.domain.repository;

import com.hshbic.skl.domain.domain.MyUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

}
