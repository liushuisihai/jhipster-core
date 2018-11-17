package com.hshbic.skl.service.mapper;

import com.hshbic.skl.domain.*;
import com.hshbic.skl.service.dto.SkillCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SkillCategory and its DTO SkillCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillCategoryMapper extends EntityMapper<SkillCategoryDTO, SkillCategory> {



    default SkillCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillCategory skillCategory = new SkillCategory();
        skillCategory.setId(id);
        return skillCategory;
    }
}
