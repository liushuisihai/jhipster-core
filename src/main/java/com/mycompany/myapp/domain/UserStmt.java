package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用户说法
 */
@ApiModel(description = "用户说法")
@Entity
@Table(name = "t_user_stmt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserStmt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户说法所属的意图Id
     */
    @NotNull
    @ApiModelProperty(value = "用户说法所属的意图Id", required = true)
    @Column(name = "intent_id", nullable = false)
    private Long intentId;

    /**
     * 用户说法内容
     */
    @NotNull
    @ApiModelProperty(value = "用户说法内容", required = true)
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 用户说法状态 -1 删除 1 正常
     */
    @NotNull
    @ApiModelProperty(value = "用户说法状态 -1 删除 1 正常", required = true)
    @Column(name = "del_flag", nullable = false)
    private Boolean delFlag;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Intent intent;

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

    public UserStmt intentId(Long intentId) {
        this.intentId = intentId;
        return this;
    }

    public void setIntentId(Long intentId) {
        this.intentId = intentId;
    }

    public String getContent() {
        return content;
    }

    public UserStmt content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isDelFlag() {
        return delFlag;
    }

    public UserStmt delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Intent getIntent() {
        return intent;
    }

    public UserStmt intent(Intent intent) {
        this.intent = intent;
        return this;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
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
        UserStmt userStmt = (UserStmt) o;
        if (userStmt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userStmt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserStmt{" +
            "id=" + getId() +
            ", intentId=" + getIntentId() +
            ", content='" + getContent() + "'" +
            ", delFlag='" + isDelFlag() + "'" +
            "}";
    }
}
