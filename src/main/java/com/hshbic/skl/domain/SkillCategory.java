package com.hshbic.skl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * 技能种类
 */
@ApiModel(description = "技能种类")
@Entity
@Table(name = "t_skill_category")
public class SkillCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 技能种类名称
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "技能种类名称", required = true)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * 技能类型的父节点id
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "技能类型的父节点id", required = true)
    @Column(name = "pid", length = 10, nullable = false)
    private String pid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SkillCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public SkillCategory pid(String pid) {
        this.pid = pid;
        return this;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkillCategory skillCategory = (SkillCategory) o;
        if (skillCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", pid='" + getPid() + "'" +
            "}";
    }
}
