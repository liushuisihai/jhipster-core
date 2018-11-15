package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 槽点对象
 */
@ApiModel(description = "槽点对象")
@Entity
@Table(name = "t_slots")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Slots implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 排序序号
     */
    @NotNull
    @ApiModelProperty(value = "排序序号", required = true)
    @Column(name = "sort_num", nullable = false)
    private Integer sortNum;

    /**
     * 是否必填 1-必填 0-否
     */
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "^(0|1)$")
    @ApiModelProperty(value = "是否必填 1-必填 0-否", required = true)
    @Column(name = "is_required", length = 1, nullable = false)
    private String isRequired;

    /**
     * 追问内容
     */
    @Size(max = 50)
    @ApiModelProperty(value = "追问内容")
    @Column(name = "question", length = 50)
    private String question;

    /**
     * 槽点状态
     */
    @ApiModelProperty(value = "槽点状态")
    @Column(name = "del_flag")
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
    private Intent intent;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Dictionary dictionary;

    @ManyToOne
    @JsonIgnoreProperties("")
    private WordGroup wordGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public Slots sortNum(Integer sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public Slots isRequired(String isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getQuestion() {
        return question;
    }

    public Slots question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean isDelFlag() {
        return delFlag;
    }

    public Slots delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Slots createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Intent getIntent() {
        return intent;
    }

    public Slots intent(Intent intent) {
        this.intent = intent;
        return this;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public Slots dictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        return this;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public WordGroup getWordGroup() {
        return wordGroup;
    }

    public Slots wordGroup(WordGroup wordGroup) {
        this.wordGroup = wordGroup;
        return this;
    }

    public void setWordGroup(WordGroup wordGroup) {
        this.wordGroup = wordGroup;
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
        Slots slots = (Slots) o;
        if (slots.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), slots.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Slots{" +
            "id=" + getId() +
            ", sortNum=" + getSortNum() +
            ", isRequired='" + getIsRequired() + "'" +
            ", question='" + getQuestion() + "'" +
            ", delFlag='" + isDelFlag() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
