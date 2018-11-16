package com.hshbic.skl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.hshbic.skl.domain.enumeration.SkillType;

import com.hshbic.skl.domain.enumeration.SkillStatus;

/**
 * 技能类
 */
@ApiModel(description = "技能类")
@Entity
@Table(name = "t_skill")
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 技能姓名
     */
    @NotNull
    @ApiModelProperty(value = "技能姓名", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 技能类型
     */
    @ApiModelProperty(value = "技能类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private SkillType type;

    /**
     * 技能状态
     */
    @ApiModelProperty(value = "技能状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SkillStatus status;

    /**
     * 技能打开名字
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "技能打开名字", required = true)
    @Column(name = "call_name", length = 50, nullable = false)
    private String callName;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private SkillCategory skillCategory;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Developer developer;

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

    public Skill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillType getType() {
        return type;
    }

    public Skill type(SkillType type) {
        this.type = type;
        return this;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    public SkillStatus getStatus() {
        return status;
    }

    public Skill status(SkillStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SkillStatus status) {
        this.status = status;
    }

    public String getCallName() {
        return callName;
    }

    public Skill callName(String callName) {
        this.callName = callName;
        return this;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Skill createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public SkillCategory getSkillCategory() {
        return skillCategory;
    }

    public Skill skillCategory(SkillCategory skillCategory) {
        this.skillCategory = skillCategory;
        return this;
    }

    public void setSkillCategory(SkillCategory skillCategory) {
        this.skillCategory = skillCategory;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public Skill developer(Developer developer) {
        this.developer = developer;
        return this;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
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
        Skill skill = (Skill) o;
        if (skill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", callName='" + getCallName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
