/*
 * This file is generated by jOOQ.
 */
package edu.java.scrapper.domain.jooq;


import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.LinksSites;
import edu.java.scrapper.domain.jooq.tables.Users;
import edu.java.scrapper.domain.jooq.tables.UsersArchive;
import edu.java.scrapper.domain.jooq.tables.UsersLinks;
import edu.java.scrapper.domain.jooq.tables.UsersLinksArchive;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Tables {

    /**
     * The table <code>LINKS</code>.
     */
    public static final Links LINKS = Links.LINKS;

    /**
     * The table <code>LINKS_SITES</code>.
     */
    public static final LinksSites LINKS_SITES = LinksSites.LINKS_SITES;

    /**
     * The table <code>USERS</code>.
     */
    public static final Users USERS = Users.USERS;

    /**
     * The table <code>USERS_ARCHIVE</code>.
     */
    public static final UsersArchive USERS_ARCHIVE = UsersArchive.USERS_ARCHIVE;

    /**
     * The table <code>USERS_LINKS</code>.
     */
    public static final UsersLinks USERS_LINKS = UsersLinks.USERS_LINKS;

    /**
     * The table <code>USERS_LINKS_ARCHIVE</code>.
     */
    public static final UsersLinksArchive USERS_LINKS_ARCHIVE = UsersLinksArchive.USERS_LINKS_ARCHIVE;
}