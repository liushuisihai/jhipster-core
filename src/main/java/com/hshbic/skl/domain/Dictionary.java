package com.hshbic.skl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.hshbic.skl.domain.enumeration.DictionaryType;

/**
 * 词典对象
 */
@ApiModel(description = "词典对象")
@Entity
@Table(name = "t_dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 词典名称
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "词典名称", required = true)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 词典类型
     */
    @ApiModelProperty(value = "词典类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private DictionaryType type;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Developer creater;

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

    public Dictionary name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DictionaryType getType() {
        return type;
    }

    public Dictionary type(DictionaryType type) {
        this.type = type;
        return this;
    }

    public void setType(DictionaryType type) {
        this.type = type;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Dictionary createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Developer getCreater() {
        return creater;
    }

    public Dictionary creater(Developer developer) {
        this.creater = developer;
        return this;
    }

    public void setCreater(Developer developer) {
        this.creater = developer;
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
        Dictionary dictionary = (Dictionary) o;
        if (dictionary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dictionary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dictionary{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
