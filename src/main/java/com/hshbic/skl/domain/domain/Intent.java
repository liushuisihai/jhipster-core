package com.hshbic.skl.domain.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * 意图对象
 */
@ApiModel(description = "意图对象")
@Entity
@Table(name = "t_intent")
public class Intent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 意图描述
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "意图描述", required = true)
    @Column(name = "content", length = 50, nullable = false)
    private String content;

    /**
     * 意图状态
     */
    @NotNull
    @ApiModelProperty(value = "意图状态", required = true)
    @Column(name = "del_flag", nullable = false)
    private Boolean delFlag;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

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

    public Boolean isDelFlag() {
        return delFlag;
    }

    public Intent delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Intent createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
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
            ", content='" + getContent() + "'" +
            ", delFlag='" + isDelFlag() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
