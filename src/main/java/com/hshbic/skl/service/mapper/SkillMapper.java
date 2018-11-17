package com.hshbic.skl.service.mapper;

import com.hshbic.skl.domain.*;
import com.hshbic.skl.service.dto.SkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Skill and its DTO SkillDTO.
 */
@Mapper(componentModel = "spring", uses = {SkillCategoryMapper.class, DeveloperMapper.class})
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {

    @Mapping(source = "skillCategory.id", target = "skillCategoryId")
    @Mapping(source = "developer.id", target = "developerId")
    SkillDTO toDto(Skill skill);

    @Mapping(source = "skillCategoryId", target = "skillCategory")
    @Mapping(source = "developerId", target = "developer")
    Skill toEntity(SkillDTO skillDTO);

    default Skill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(id);
        return skill;
    }
}
