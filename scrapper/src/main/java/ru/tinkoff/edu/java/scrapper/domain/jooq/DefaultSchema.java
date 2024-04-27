/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;


import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinksSites;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Users;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.UsersLinks;


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
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>LINKS</code>.
     */
    public final Links LINKS = Links.LINKS;

    /**
     * The table <code>LINKS_SITES</code>.
     */
    public final LinksSites LINKS_SITES = LinksSites.LINKS_SITES;

    /**
     * The table <code>USERS</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * The table <code>USERS_LINKS</code>.
     */
    public final UsersLinks USERS_LINKS = UsersLinks.USERS_LINKS;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    @NotNull
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    @NotNull
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Links.LINKS,
            LinksSites.LINKS_SITES,
            Users.USERS,
            UsersLinks.USERS_LINKS
        );
    }
}
