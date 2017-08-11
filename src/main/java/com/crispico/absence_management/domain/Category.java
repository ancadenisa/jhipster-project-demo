package com.crispico.absence_management.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * <jhipster-entity-replacer>
     * replacer.replaceRegex(\"@Id[\\s\\S]*?;\", \"\");
     * replacer.storeReplacements(\"defaultAbstractEntity\",
     * function(replacer) {
     * replacer.replaceRegex(\"@Override\\s*public int hashCode\\([\\s\\S]*?\\)\\s*{[\\s\\S]*?}\\s*(?=[\\w\\W])\", \"\")
     * }
     * );
     * replacer.insertElement('@Named');
     * </jhipster-entity-replacer>
     */
    @NotNull
    @Size(min = 2)
    @ApiModelProperty(value = "<jhipster-entity-replacer>replacer.replaceRegex("@Id[\\s\\S]*?;", ""); replacer.storeReplacements("defaultAbstractEntity", function(replacer) { replacer.replaceRegex("@Override\\s*public int hashCode\\([\\s\\S]*?\\)\\s*{[\\s\\S]*?}\\s*(?=[\\w\\W])", "") } ); replacer.insertElement('@Named');</jhipster-entity-replacer>", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
