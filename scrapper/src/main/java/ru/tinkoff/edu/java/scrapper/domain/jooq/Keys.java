/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;


import javax.annotation.processing.Generated;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinksSites;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Users;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.UsersLinks;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinksSitesRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.UsersLinksRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.UsersRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<LinksRecord> CONSTRAINT_45 = Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_45"), new TableField[] { Links.LINKS.LINK_ID }, true);
    public static final UniqueKey<LinksSitesRecord> CONSTRAINT_46 = Internal.createUniqueKey(LinksSites.LINKS_SITES, DSL.name("CONSTRAINT_46"), new TableField[] { LinksSites.LINKS_SITES.ID }, true);
    public static final UniqueKey<UsersRecord> CONSTRAINT_4 = Internal.createUniqueKey(Users.USERS, DSL.name("CONSTRAINT_4"), new TableField[] { Users.USERS.CHAT_ID }, true);
    public static final UniqueKey<UsersLinksRecord> CONSTRAINT_C = Internal.createUniqueKey(UsersLinks.USERS_LINKS, DSL.name("CONSTRAINT_C"), new TableField[] { UsersLinks.USERS_LINKS.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<LinksRecord, LinksSitesRecord> FK_LINKS_SITES = Internal.createForeignKey(Links.LINKS, DSL.name("FK_LINKS_SITES"), new TableField[] { Links.LINKS.SITE_ID }, Keys.CONSTRAINT_46, new TableField[] { LinksSites.LINKS_SITES.ID }, true);
    public static final ForeignKey<UsersLinksRecord, UsersRecord> CONSTRAINT_C6 = Internal.createForeignKey(UsersLinks.USERS_LINKS, DSL.name("CONSTRAINT_C6"), new TableField[] { UsersLinks.USERS_LINKS.USER_ID }, Keys.CONSTRAINT_4, new TableField[] { Users.USERS.CHAT_ID }, true);
    public static final ForeignKey<UsersLinksRecord, LinksRecord> CONSTRAINT_C67 = Internal.createForeignKey(UsersLinks.USERS_LINKS, DSL.name("CONSTRAINT_C67"), new TableField[] { UsersLinks.USERS_LINKS.LINK_ID }, Keys.CONSTRAINT_45, new TableField[] { Links.LINKS.LINK_ID }, true);
}
