package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * 任务描述
 */
@ApiModel(description = "任务描述")
@Entity
@Table(name = "t_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务描述
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "任务描述", required = true)
    @Column(name = "content", length = 50, nullable = false)
    private String content;

    /**
     * 技能id
     */
    @NotNull
    @ApiModelProperty(value = "技能id", required = true)
    @Column(name = "skill_id", nullable = false)
    private Long skillId;

    /**
     * 技能状态 -1 删除 1 正常
     */
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "^(-1|1)$")
    @ApiModelProperty(value = "技能状态 -1 删除 1 正常", required = true)
    @Column(name = "status", length = 1, nullable = false)
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Task content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSkillId() {
        return skillId;
    }

    public Task skillId(Long skillId) {
        this.skillId = skillId;
        return this;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getStatus() {
        return status;
    }

    public Task status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", skillId=" + getSkillId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
