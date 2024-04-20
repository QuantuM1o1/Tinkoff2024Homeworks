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
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chatId;
    private OffsetDateTime addedAt;
    private OffsetDateTime deletedAt;

    public Users() {}

    public Users(Users value) {
        this.chatId = value.chatId;
        this.addedAt = value.addedAt;
        this.deletedAt = value.deletedAt;
    }

    @ConstructorProperties({ "chatId", "addedAt", "deletedAt" })
    public Users(
        @NotNull Long chatId,
        @NotNull OffsetDateTime addedAt,
        @Nullable OffsetDateTime deletedAt
    ) {
        this.chatId = chatId;
        this.addedAt = addedAt;
        this.deletedAt = deletedAt;
    }

    /**
     * Getter for <code>USERS.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>USERS.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for <code>USERS.ADDED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getAddedAt() {
        return this.addedAt;
    }

    /**
     * Setter for <code>USERS.ADDED_AT</code>.
     */
    public void setAddedAt(@NotNull OffsetDateTime addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * Getter for <code>USERS.DELETED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getDeletedAt() {
        return this.deletedAt;
    }

    /**
     * Setter for <code>USERS.DELETED_AT</code>.
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
        final Users other = (Users) obj;
        if (this.chatId == null) {
            if (other.chatId != null)
                return false;
        }
        else if (!this.chatId.equals(other.chatId))
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
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        result = prime * result + ((this.addedAt == null) ? 0 : this.addedAt.hashCode());
        result = prime * result + ((this.deletedAt == null) ? 0 : this.deletedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Users (");

        sb.append(chatId);
        sb.append(", ").append(addedAt);
        sb.append(", ").append(deletedAt);

        sb.append(")");
        return sb.toString();
    }
}