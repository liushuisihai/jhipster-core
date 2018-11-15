package com.hshbic.skl.domain.repository;

import com.hshbic.skl.domain.domain.Slots;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Slots entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlotsRepository extends JpaRepository<Slots, Long> {

}
