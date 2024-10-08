/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq.tables;


import edu.java.scrapper.domain.jooq.DefaultSchema;
import edu.java.scrapper.domain.jooq.Keys;
import edu.java.scrapper.domain.jooq.tables.records.UsersLinksRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


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
public class UsersLinks extends TableImpl<UsersLinksRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>USERS_LINKS</code>
     */
    public static final UsersLinks USERS_LINKS = new UsersLinks();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<UsersLinksRecord> getRecordType() {
        return UsersLinksRecord.class;
    }

    /**
     * The column <code>USERS_LINKS.USER_ID</code>.
     */
    public final TableField<UsersLinksRecord, Long> USER_ID = createField(DSL.name("USER_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>USERS_LINKS.LINK_ID</code>.
     */
    public final TableField<UsersLinksRecord, Long> LINK_ID = createField(DSL.name("LINK_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    private UsersLinks(Name alias, Table<UsersLinksRecord> aliased) {
        this(alias, aliased, null);
    }

    private UsersLinks(Name alias, Table<UsersLinksRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>USERS_LINKS</code> table reference
     */
    public UsersLinks(String alias) {
        this(DSL.name(alias), USERS_LINKS);
    }

    /**
     * Create an aliased <code>USERS_LINKS</code> table reference
     */
    public UsersLinks(Name alias) {
        this(alias, USERS_LINKS);
    }

    /**
     * Create a <code>USERS_LINKS</code> table reference
     */
    public UsersLinks() {
        this(DSL.name("USERS_LINKS"), null);
    }

    public <O extends Record> UsersLinks(Table<O> child, ForeignKey<O, UsersLinksRecord> key) {
        super(child, key, USERS_LINKS);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public UniqueKey<UsersLinksRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_AD64;
    }

    @Override
    @NotNull
    public List<ForeignKey<UsersLinksRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_AD, Keys.CONSTRAINT_AD6);
    }

    private transient Users _users;
    private transient Links _links;

    /**
     * Get the implicit join path to the <code>PUBLIC.USERS</code> table.
     */
    public Users users() {
        if (_users == null)
            _users = new Users(this, Keys.CONSTRAINT_AD);

        return _users;
    }

    /**
     * Get the implicit join path to the <code>PUBLIC.LINKS</code> table.
     */
    public Links links() {
        if (_links == null)
            _links = new Links(this, Keys.CONSTRAINT_AD6);

        return _links;
    }

    @Override
    @NotNull
    public UsersLinks as(String alias) {
        return new UsersLinks(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public UsersLinks as(Name alias) {
        return new UsersLinks(alias, this);
    }

    @Override
    @NotNull
    public UsersLinks as(Table<?> alias) {
        return new UsersLinks(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public UsersLinks rename(String name) {
        return new UsersLinks(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public UsersLinks rename(Name name) {
        return new UsersLinks(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public UsersLinks rename(Table<?> name) {
        return new UsersLinks(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
