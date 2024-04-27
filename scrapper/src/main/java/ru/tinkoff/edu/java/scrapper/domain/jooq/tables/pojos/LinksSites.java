/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class LinksSites implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String domainName;

    public LinksSites() {}

    public LinksSites(LinksSites value) {
        this.id = value.id;
        this.domainName = value.domainName;
    }

    @ConstructorProperties({ "id", "domainName" })
    public LinksSites(
        @NotNull Integer id,
        @NotNull String domainName
    ) {
        this.id = id;
        this.domainName = domainName;
    }

    /**
     * Getter for <code>LINKS_SITES.ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINKS_SITES.ID</code>.
     */
    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINKS_SITES.DOMAIN_NAME</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 255)
    @NotNull
    public String getDomainName() {
        return this.domainName;
    }

    /**
     * Setter for <code>LINKS_SITES.DOMAIN_NAME</code>.
     */
    public void setDomainName(@NotNull String domainName) {
        this.domainName = domainName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final LinksSites other = (LinksSites) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.domainName == null) {
            if (other.domainName != null)
                return false;
        }
        else if (!this.domainName.equals(other.domainName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.domainName == null) ? 0 : this.domainName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LinksSites (");

        sb.append(id);
        sb.append(", ").append(domainName);

        sb.append(")");
        return sb.toString();
    }
}
