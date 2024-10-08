/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.tables.records;


import edu.java.scrapper.domain.jooq.tables.Users;

import java.beans.ConstructorProperties;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record1<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>USERS.TG_CHAT_ID</code>.
     */
    public void setTgChatId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>USERS.TG_CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getTgChatId() {
        return (Long) get(0);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row1<Long> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row1<Long> valuesRow() {
        return (Row1) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Users.USERS.TG_CHAT_ID;
    }

    @Override
    @NotNull
    public Long component1() {
        return getTgChatId();
    }

    @Override
    @NotNull
    public Long value1() {
        return getTgChatId();
    }

    @Override
    @NotNull
    public UsersRecord value1(@NotNull Long value) {
        setTgChatId(value);
        return this;
    }

    @Override
    @NotNull
    public UsersRecord values(@NotNull Long value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    @ConstructorProperties({ "tgChatId" })
    public UsersRecord(@NotNull Long tgChatId) {
        super(Users.USERS);

        setTgChatId(tgChatId);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(edu.java.scrapper.domain.jooq.tables.pojos.Users value) {
        super(Users.USERS);

        if (value != null) {
            setTgChatId(value.getTgChatId());
            resetChangedOnNotNull();
        }
    }
}
