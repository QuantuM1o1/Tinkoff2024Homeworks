/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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

    private Long id;
    private Long userId;
    private Long linkId;
    private OffsetDateTime deletedAt;

    public UsersLinks() {}

    public UsersLinks(UsersLinks value) {
        this.id = value.id;
        this.userId = value.userId;
        this.linkId = value.linkId;
        this.deletedAt = value.deletedAt;
    }

    @ConstructorProperties({ "id", "userId", "linkId", "deletedAt" })
    public UsersLinks(
        @Nullable Long id,
        @NotNull Long userId,
        @NotNull Long linkId,
        @Nullable OffsetDateTime deletedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.linkId = linkId;
        this.deletedAt = deletedAt;
    }

    /**
     * Getter for <code>USERS_LINKS.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>USERS_LINKS.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
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

    /**
     * Getter for <code>USERS_LINKS.DELETED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getDeletedAt() {
        return this.deletedAt;
    }

    /**
     * Setter for <code>USERS_LINKS.DELETED_AT</code>.
     */
    public void setDeletedAt(@Nullable OffsetDateTime deletedAt) {
        this.deletedAt = deletedAt;
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
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
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
        if (this.deletedAt == null) {
            if (other.deletedAt != null)
                return false;
        }
        else if (!this.deletedAt.equals(other.deletedAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        result = prime * result + ((this.deletedAt == null) ? 0 : this.deletedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UsersLinks (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(linkId);
        sb.append(", ").append(deletedAt);

        sb.append(")");
        return sb.toString();
    }
}