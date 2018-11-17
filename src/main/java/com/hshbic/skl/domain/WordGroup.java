package com.hshbic.skl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * 词组
 */
@ApiModel(description = "词组")
@Entity
@Table(name = "t_word_group")
public class WordGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 词组内容
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "词组内容", required = true)
    @Column(name = "content", length = 20, nullable = false)
    private String content;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Dictionary dictionary;

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

    public WordGroup content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public WordGroup dictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        return this;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
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
        WordGroup wordGroup = (WordGroup) o;
        if (wordGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wordGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WordGroup{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}