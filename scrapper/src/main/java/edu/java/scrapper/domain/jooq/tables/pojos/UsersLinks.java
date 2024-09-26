/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.tables.pojos;


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
public class UsersLinks implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long linkId;

    public UsersLinks() {}

    public UsersLinks(UsersLinks value) {
        this.userId = value.userId;
        this.linkId = value.linkId;
    }

    @ConstructorProperties({ "userId", "linkId" })
    public UsersLinks(
        @NotNull Long userId,
        @NotNull Long linkId
    ) {
        this.userId = userId;
        this.linkId = linkId;
    }

    /**
     * Getter for <code>USERS_LINKS.USER_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getUserId() {
        return this.userId;
    }

    /**
     * Setter for <code>USERS_LINKS.USER_ID</code>.
     */
    public void setUserId(@NotNull Long userId) {
        this.userId = userId;
    }

    /**
     * Getter for <code>USERS_LINKS.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>USERS_LINKS.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long linkId) {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UsersLinks other = (UsersLinks) obj;
        if (this.userId == null) {
            if (other.userId != null)
                return false;
        }
        else if (!this.userId.equals(other.userId))
            return false;
        if (this.linkId == null) {
            if (other.linkId != null)
                return false;
        }
        else if (!this.linkId.equals(other.linkId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UsersLinks (");

        sb.append(userId);
        sb.append(", ").append(linkId);

        sb.append(")");
        return sb.toString();
    }
}
