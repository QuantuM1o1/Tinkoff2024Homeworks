/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


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
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long linkId;
    private String url;
    private OffsetDateTime addedAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime deletedAt;
    private OffsetDateTime lastActivity;
    private Integer siteId;
    private Integer answerCount;
    private Integer commentCount;

    public Links() {}

    public Links(Links value) {
        this.linkId = value.linkId;
        this.url = value.url;
        this.addedAt = value.addedAt;
        this.updatedAt = value.updatedAt;
        this.deletedAt = value.deletedAt;
        this.lastActivity = value.lastActivity;
        this.siteId = value.siteId;
        this.answerCount = value.answerCount;
        this.commentCount = value.commentCount;
    }

    @ConstructorProperties({ "linkId", "url", "addedAt", "updatedAt", "deletedAt", "lastActivity", "siteId", "answerCount", "commentCount" })
    public Links(
        @Nullable Long linkId,
        @NotNull String url,
        @NotNull OffsetDateTime addedAt,
        @NotNull OffsetDateTime updatedAt,
        @Nullable OffsetDateTime deletedAt,
        @NotNull OffsetDateTime lastActivity,
        @NotNull Integer siteId,
        @Nullable Integer answerCount,
        @Nullable Integer commentCount
    ) {
        this.linkId = linkId;
        this.url = url;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.lastActivity = lastActivity;
        this.siteId = siteId;
        this.answerCount = answerCount;
        this.commentCount = commentCount;
    }

    /**
     * Getter for <code>LINKS.LINK_ID</code>.
     */
    @Nullable
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>LINKS.LINK_ID</code>.
     */
    public void setLinkId(@Nullable Long linkId) {
        this.linkId = linkId;
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 255)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINKS.ADDED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getAddedAt() {
        return this.addedAt;
    }

    /**
     * Setter for <code>LINKS.ADDED_AT</code>.
     */
    public void setAddedAt(@NotNull OffsetDateTime addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * Getter for <code>LINKS.UPDATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Setter for <code>LINKS.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@NotNull OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>LINKS.DELETED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getDeletedAt() {
        return this.deletedAt;
    }

    /**
     * Setter for <code>LINKS.DELETED_AT</code>.
     */
    public void setDeletedAt(@Nullable OffsetDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * Getter for <code>LINKS.LAST_ACTIVITY</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getLastActivity() {
        return this.lastActivity;
    }

    /**
     * Setter for <code>LINKS.LAST_ACTIVITY</code>.
     */
    public void setLastActivity(@NotNull OffsetDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    /**
     * Getter for <code>LINKS.SITE_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Integer getSiteId() {
        return this.siteId;
    }

    /**
     * Setter for <code>LINKS.SITE_ID</code>.
     */
    public void setSiteId(@NotNull Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * Getter for <code>LINKS.ANSWER_COUNT</code>.
     */
    @Nullable
    public Integer getAnswerCount() {
        return this.answerCount;
    }

    /**
     * Setter for <code>LINKS.ANSWER_COUNT</code>.
     */
    public void setAnswerCount(@Nullable Integer answerCount) {
        this.answerCount = answerCount;
    }

    /**
     * Getter for <code>LINKS.COMMENT_COUNT</code>.
     */
    @Nullable
    public Integer getCommentCount() {
        return this.commentCount;
    }

    /**
     * Setter for <code>LINKS.COMMENT_COUNT</code>.
     */
    public void setCommentCount(@Nullable Integer commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Links other = (Links) obj;
        if (this.linkId == null) {
            if (other.linkId != null)
                return false;
        }
        else if (!this.linkId.equals(other.linkId))
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
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        if (this.deletedAt == null) {
            if (other.deletedAt != null)
                return false;
        }
        else if (!this.deletedAt.equals(other.deletedAt))
            return false;
        if (this.lastActivity == null) {
            if (other.lastActivity != null)
                return false;
        }
        else if (!this.lastActivity.equals(other.lastActivity))
            return false;
        if (this.siteId == null) {
            if (other.siteId != null)
                return false;
        }
        else if (!this.siteId.equals(other.siteId))
            return false;
        if (this.answerCount == null) {
            if (other.answerCount != null)
                return false;
        }
        else if (!this.answerCount.equals(other.answerCount))
            return false;
        if (this.commentCount == null) {
            if (other.commentCount != null)
                return false;
        }
        else if (!this.commentCount.equals(other.commentCount))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.addedAt == null) ? 0 : this.addedAt.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        result = prime * result + ((this.deletedAt == null) ? 0 : this.deletedAt.hashCode());
        result = prime * result + ((this.lastActivity == null) ? 0 : this.lastActivity.hashCode());
        result = prime * result + ((this.siteId == null) ? 0 : this.siteId.hashCode());
        result = prime * result + ((this.answerCount == null) ? 0 : this.answerCount.hashCode());
        result = prime * result + ((this.commentCount == null) ? 0 : this.commentCount.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Links (");

        sb.append(linkId);
        sb.append(", ").append(url);
        sb.append(", ").append(addedAt);
        sb.append(", ").append(updatedAt);
        sb.append(", ").append(deletedAt);
        sb.append(", ").append(lastActivity);
        sb.append(", ").append(siteId);
        sb.append(", ").append(answerCount);
        sb.append(", ").append(commentCount);

        sb.append(")");
        return sb.toString();
    }
}
