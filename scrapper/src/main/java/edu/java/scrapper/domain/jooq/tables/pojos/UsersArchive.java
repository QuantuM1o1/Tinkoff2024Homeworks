/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.tables.pojos;


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
public class UsersArchive implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long tgChatId;
    private OffsetDateTime addedAt;
    private OffsetDateTime deletedAt;

    public UsersArchive() {}

    public UsersArchive(UsersArchive value) {
        this.id = value.id;
        this.tgChatId = value.tgChatId;
        this.addedAt = value.addedAt;
        this.deletedAt = value.deletedAt;
    }

    @ConstructorProperties({ "id", "tgChatId", "addedAt", "deletedAt" })
    public UsersArchive(
        @Nullable Long id,
        @Nullable Long tgChatId,
        @NotNull OffsetDateTime addedAt,
        @Nullable OffsetDateTime deletedAt
    ) {
        this.id = id;
        this.tgChatId = tgChatId;
        this.addedAt = addedAt;
        this.deletedAt = deletedAt;
    }

    /**
     * Getter for <code>USERS_ARCHIVE.ID</code>.
     */
    @Nullable
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>USERS_ARCHIVE.ID</code>.
     */
    public void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>USERS_ARCHIVE.TG_CHAT_ID</code>.
     */
    @Nullable
    public Long getTgChatId() {
        return this.tgChatId;
    }

    /**
     * Setter for <code>USERS_ARCHIVE.TG_CHAT_ID</code>.
     */
    public void setTgChatId(@Nullable Long tgChatId) {
        this.tgChatId = tgChatId;
    }

    /**
     * Getter for <code>USERS_ARCHIVE.ADDED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getAddedAt() {
        return this.addedAt;
    }

    /**
     * Setter for <code>USERS_ARCHIVE.ADDED_AT</code>.
     */
    public void setAddedAt(@NotNull OffsetDateTime addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * Getter for <code>USERS_ARCHIVE.DELETED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getDeletedAt() {
        return this.deletedAt;
    }

    /**
     * Setter for <code>USERS_ARCHIVE.DELETED_AT</code>.
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
        final UsersArchive other = (UsersArchive) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.tgChatId == null) {
            if (other.tgChatId != null)
                return false;
        }
        else if (!this.tgChatId.equals(other.tgChatId))
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
        result = prime * result + ((this.tgChatId == null) ? 0 : this.tgChatId.hashCode());
        result = prime * result + ((this.addedAt == null) ? 0 : this.addedAt.hashCode());
        result = prime * result + ((this.deletedAt == null) ? 0 : this.deletedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UsersArchive (");

        sb.append(id);
        sb.append(", ").append(tgChatId);
        sb.append(", ").append(addedAt);
        sb.append(", ").append(deletedAt);

        sb.append(")");
        return sb.toString();
    }
}
