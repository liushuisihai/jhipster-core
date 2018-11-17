package com.hshbic.skl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * App用户
 */
@ApiModel(description = "App用户")
@Entity
@Table(name = "t_user")
public class MyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "用户名", required = true)
    @Column(name = "username", length = 20, nullable = false)
    private String username;

    /**
     * 用户密码
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "用户密码", required = true)
    @Column(name = "jhi_password", length = 20, nullable = false)
    private String password;

    /**
     * 用户头像
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "用户头像", required = true)
    @Column(name = "photo", length = 50, nullable = false)
    private String photo;

    /**
     * app交互token
     */
    @Size(max = 30)
    @ApiModelProperty(value = "app交互token")
    @Column(name = "token", length = 30)
    private String token;

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

    public MyUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public MyUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public MyUser photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public MyUser token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
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
        MyUser myUser = (MyUser) o;
        if (myUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
