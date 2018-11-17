package com.hshbic.skl.repository;

import com.hshbic.skl.domain.Dictionary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dictionary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

}
