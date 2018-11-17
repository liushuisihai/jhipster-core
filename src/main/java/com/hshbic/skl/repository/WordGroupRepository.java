package com.hshbic.skl.repository;

import com.hshbic.skl.domain.WordGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WordGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordGroupRepository extends JpaRepository<WordGroup, Long> {

}
