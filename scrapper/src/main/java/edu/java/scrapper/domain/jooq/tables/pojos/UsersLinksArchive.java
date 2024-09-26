/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

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
public class UsersLinksArchive implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String url;
    private OffsetDateTime addedAt;
    private OffsetDateTime deletedAt;

    public UsersLinksArchive() {}

    public UsersLinksArchive(UsersLinksArchive value) {
        this.id = value.id;
        this.userId = value.userId;
        this.url = value.url;
        this.addedAt = value.addedAt;
        this.deletedAt = value.deletedAt;
    }

    @ConstructorProperties({ "id", "userId", "url", "addedAt", "deletedAt" })
    public UsersLinksArchive(
        @Nullable Long id,
        @NotNull Long userId,
        @NotNull String url,
        @NotNull OffsetDateTime addedAt,
        @Nullable OffsetDateTime deletedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.url = url;
        this.addedAt = addedAt;
        this.deletedAt = deletedAt;
    }

    /**
     * Getter for <code>USERS_LINKS_ARCHIVE.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>USERS_LINKS_ARCHIVE.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>USERS_LINKS_ARCHIVE.USER_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getUserId() {
        return this.userId;
    }

    /**
     * Setter for <code>USERS_LINKS_ARCHIVE.USER_ID</code>.
     */
    public void setUserId(@NotNull Long userId) {
        this.userId = userId;
    }

    /**
     * Getter for <code>USERS_LINKS_ARCHIVE.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>USERS_LINKS_ARCHIVE.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>USERS_LINKS_ARCHIVE.ADDED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getAddedAt() {
        return this.addedAt;
    }

    /**
     * Setter for <code>USERS_LINKS_ARCHIVE.ADDED_AT</code>.
     */
    public void setAddedAt(@NotNull OffsetDateTime addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * Getter for <code>USERS_LINKS_ARCHIVE.DELETED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getDeletedAt() {
        return this.deletedAt;
    }

    /**
     * Setter for <code>USERS_LINKS_ARCHIVE.DELETED_AT</code>.
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
        final UsersLinksArchive other = (UsersLinksArchive) obj;
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
        if (this.url == null) {
            if (other.url != null)
                return false;
        }
        else if (!this.url.equals(other.url))
            return false;
        if (this.addedAt == null) {
            if (other.addedAt != null)
                return false;
        }
        else if (!this.addedAt.equals(other.addedAt))
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
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.addedAt == null) ? 0 : this.addedAt.hashCode());
        result = prime * result + ((this.deletedAt == null) ? 0 : this.deletedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UsersLinksArchive (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(url);
        sb.append(", ").append(addedAt);
        sb.append(", ").append(deletedAt);

        sb.append(")");
        return sb.toString();
    }
}
