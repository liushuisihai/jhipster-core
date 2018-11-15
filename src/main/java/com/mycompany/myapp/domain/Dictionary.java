package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * 词典对象
 */
@ApiModel(description = "词典对象")
@Entity
@Table(name = "t_dictionary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
     * 词典类型 1-系统 2-自定义
     */
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "^(1|2)$")
    @ApiModelProperty(value = "词典类型 1-系统 2-自定义", required = true)
    @Column(name = "jhi_type", length = 1, nullable = false)
    private String type;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

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

    public String getType() {
        return type;
    }

    public Dictionary type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
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
