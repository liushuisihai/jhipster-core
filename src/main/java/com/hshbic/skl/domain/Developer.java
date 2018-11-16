package com.hshbic.skl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.hshbic.skl.domain.enumeration.DeveloperType;

/**
 * 开发者对象
 */
@ApiModel(description = "开发者对象")
@Entity
@Table(name = "t_developer")
public class Developer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 开发者姓名
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "开发者姓名", required = true)
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    /**
     * 密码
     */
    @NotNull
    @Size(max = 30)
    @ApiModelProperty(value = "密码", required = true)
    @Column(name = "jhi_password", length = 30, nullable = false)
    private String password;

    /**
     * 邮箱
     */
    @NotNull
    @Size(max = 30)
    @ApiModelProperty(value = "邮箱", required = true)
    @Column(name = "email", length = 30, nullable = false)
    private String email;

    /**
     * 开发者类型
     */
    @ApiModelProperty(value = "开发者类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private DeveloperType type;

    /**
     * 创建日期
     */
    @NotNull
    @ApiModelProperty(value = "创建日期", required = true)
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Developer username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Developer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Developer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DeveloperType getType() {
        return type;
    }

    public Developer type(DeveloperType type) {
        this.type = type;
        return this;
    }

    public void setType(DeveloperType type) {
        this.type = type;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Developer createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
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
        Developer developer = (Developer) o;
        if (developer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), developer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Developer{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
