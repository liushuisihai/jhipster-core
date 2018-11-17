package com.hshbic.skl.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SkillCategory entity.
 */
public class SkillCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String name;

    @NotNull
    @Size(max = 10)
    private String pid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillCategoryDTO skillCategoryDTO = (SkillCategoryDTO) o;
        if (skillCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pid='" + getPid() + "'" +
            "}";
    }
}
