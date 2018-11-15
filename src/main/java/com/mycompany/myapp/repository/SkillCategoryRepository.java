package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SkillCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SkillCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {

}
