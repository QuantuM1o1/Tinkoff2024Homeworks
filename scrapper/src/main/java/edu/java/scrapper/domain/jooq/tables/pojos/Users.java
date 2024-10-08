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
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tgChatId;

    public Users() {}

    public Users(Users value) {
        this.tgChatId = value.tgChatId;
    }

    @ConstructorProperties({ "tgChatId" })
    public Users(
        @NotNull Long tgChatId
    ) {
        this.tgChatId = tgChatId;
    }

    /**
     * Getter for <code>USERS.TG_CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTgChatId() {
        return this.tgChatId;
    }

    /**
     * Setter for <code>USERS.TG_CHAT_ID</code>.
     */
    public void setTgChatId(@NotNull Long tgChatId) {
        this.tgChatId = tgChatId;
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
        if (this.tgChatId == null) {
            if (other.tgChatId != null)
                return false;
        }
        else if (!this.tgChatId.equals(other.tgChatId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.tgChatId == null) ? 0 : this.tgChatId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Users (");

        sb.append(tgChatId);

        sb.append(")");
        return sb.toString();
    }
}
