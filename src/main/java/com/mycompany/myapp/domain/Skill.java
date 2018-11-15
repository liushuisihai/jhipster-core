package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 技能类
 */
@ApiModel(description = "技能类")
@Entity
@Table(name = "t_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 开发者Id
     */
    @NotNull
    @ApiModelProperty(value = "开发者Id", required = true)
    @Column(name = "developer_id", nullable = false)
    private Long developerId;

    /**
     * 技能类别id
     */
    @NotNull
    @ApiModelProperty(value = "技能类别id", required = true)
    @Column(name = "category_id", nullable = false)
    private String categoryId;

    /**
     * 技能姓名
     */
    @NotNull
    @ApiModelProperty(value = "技能姓名", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 技能类型 1-目的型 2-问答型 3-自定义
     */
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "^(1|2|3)$")
    @ApiModelProperty(value = "技能类型 1-目的型 2-问答型 3-自定义", required = true)
    @Column(name = "jhi_type", length = 1, nullable = false)
    private String type;

    /**
     * 技能状态 1-开发中 2-审核中 3-已发布 -1已删除
     */
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "^(1|2|3|-1)$")
    @ApiModelProperty(value = "技能状态 1-开发中 2-审核中 3-已发布 -1已删除", required = true)
    @Column(name = "status", length = 1, nullable = false)
    private String status;

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
    private LocalDate createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public Skill developerId(Long developerId) {
        this.developerId = developerId;
        return this;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Skill categoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getType() {
        return type;
    }

    public Skill type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Skill status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
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

    public LocalDate getCreateTime() {
        return createTime;
    }

    public Skill createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
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
            ", developerId=" + getDeveloperId() +
            ", categoryId='" + getCategoryId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", callName='" + getCallName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
