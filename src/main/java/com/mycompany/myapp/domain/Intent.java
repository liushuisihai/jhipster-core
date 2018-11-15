package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 意图对象
 */
@ApiModel(description = "意图对象")
@Entity
@Table(name = "t_intent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Intent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 意图id
     */
    @NotNull
    @ApiModelProperty(value = "意图id", required = true)
    @Column(name = "intent_id", nullable = false)
    private Long intentId;

    /**
     * 技能id
     */
    @NotNull
    @ApiModelProperty(value = "技能id", required = true)
    @Column(name = "skill_id", nullable = false)
    private Long skillId;

    /**
     * 意图描述
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "意图描述", required = true)
    @Column(name = "content", length = 50, nullable = false)
    private String content;

    /**
     * 意图状态 1-正常 -1 已删除
     */
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "^(1|2)$")
    @ApiModelProperty(value = "意图状态 1-正常 -1 已删除", required = true)
    @Column(name = "status", length = 1, nullable = false)
    private String status;

    /**
     * 任务Id
     */
    @NotNull
    @ApiModelProperty(value = "任务Id", required = true)
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private LocalDate createTime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIntentId() {
        return intentId;
    }

    public Intent intentId(Long intentId) {
        this.intentId = intentId;
        return this;
    }

    public void setIntentId(Long intentId) {
        this.intentId = intentId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public Intent skillId(Long skillId) {
        this.skillId = skillId;
        return this;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getContent() {
        return content;
    }

    public Intent content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public Intent status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Intent taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public Intent createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public Task getTask() {
        return task;
    }

    public Intent task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
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
        Intent intent = (Intent) o;
        if (intent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Intent{" +
            "id=" + getId() +
            ", intentId=" + getIntentId() +
            ", skillId=" + getSkillId() +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", taskId=" + getTaskId() +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
