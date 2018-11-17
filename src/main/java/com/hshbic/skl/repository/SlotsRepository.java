package com.hshbic.skl.repository;

import com.hshbic.skl.domain.Slots;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Slots entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlotsRepository extends JpaRepository<Slots, Long> {

}
