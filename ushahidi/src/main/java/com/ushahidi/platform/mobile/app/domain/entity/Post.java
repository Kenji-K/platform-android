/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.platform.mobile.app.domain.entity;

import com.addhen.android.raiburari.domain.entity.Entity;

import java.util.Date;
import java.util.List;

/**
 * A post entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */

public class Post extends Entity {

    private Long mParent;

    private Type mType;

    private String mTitle;

    private String mSlug;

    private String mContent;

    private String mAuthorEmail;

    private String mAuthorRealname;

    private Status mStatus;

    private Date mCreated;

    private Date mUpdated;

    private Long mDeploymentId;

    private PostForm mPostForm;

    private PostCompletedStages mCompletedStages;

    private AllowedPrivileges mAllowedPrivileges;

    private PostUser mPostUser;

    private PostPublishedTo mPostPublishedTo;

    // Store the raw JSON for this field. This is a dynamic field
    // and can't predict its keys.
    private PostValue mValues;

    private List<Tag> mTags;

    public PostPublishedTo getPostPublishedTo() {
        return mPostPublishedTo;
    }

    public void setPostPublishedTo(PostPublishedTo postPublishedTo) {
        mPostPublishedTo = postPublishedTo;
    }

    public Long getParent() {
        return mParent;
    }

    public void setParent(Long parent) {
        mParent = parent;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getAuthorRealname() {
        return mAuthorRealname;
    }

    public void setAuthorRealname(String authorRealname) {
        mAuthorRealname = authorRealname;
    }

    public String getAuthorEmail() {
        return mAuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        mAuthorEmail = authorEmail;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        mCreated = created;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }

    public PostValue getValues() {
        return mValues;
    }

    public void setValues(PostValue values) {
        mValues = values;
    }

    public List<Tag> getTags() {
        return mTags;
    }

    public void setTags(List<Tag> tags) {
        mTags = tags;
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    public PostForm getPostForm() {
        return mPostForm;
    }

    public void setPostForm(PostForm postForm) {
        mPostForm = postForm;
    }

    public PostCompletedStages getCompletedStages() {
        return mCompletedStages;
    }

    public void setCompletedStages(PostCompletedStages completedStages) {
        mCompletedStages = completedStages;
    }

    public AllowedPrivileges getAllowedPrivileges() {
        return mAllowedPrivileges;
    }

    public void setAllowedPrivileges(AllowedPrivileges allowedPrivileges) {
        mAllowedPrivileges = allowedPrivileges;
    }

    public PostUser getPostUser() {
        return mPostUser;
    }

    public void setPostUser(PostUser postUser) {
        mPostUser = postUser;
    }

    @Override
    public String toString() {
        return "Post{"
                + "mParent=" + mParent
                + ", mType=" + mType
                + ", mTitle='" + mTitle + '\''
                + ", mSlug='" + mSlug + '\''
                + ", mContent='" + mContent + '\''
                + ", mAuthorEmail='" + mAuthorEmail + '\''
                + ", mAuthorRealname='" + mAuthorRealname + '\''
                + ", mStatus=" + mStatus
                + ", mCreated=" + mCreated
                + ", mUpdated=" + mUpdated
                + ", mDeploymentId=" + mDeploymentId
                + ", mPostForm=" + mPostForm
                + ", mCompletedStages=" + mCompletedStages
                + ", mAllowedPrivileges=" + mAllowedPrivileges
                + ", mPostUser=" + mPostUser
                + ", mValues=" + mValues
                + ", mTags=" + mTags
                + '}';
    }


    public enum Status {
        /**
         * A draft status
         */
        DRAFT("draft"),

        /**
         * A published status
         */
        PUBLISHED("published"),

        /**
         * A pending status
         */
        PENDING("pending"),

        /**
         * An unknown status
         */
        UNKNOWN("unknown");

        private String value;

        /**
         * The value property of the post
         *
         * @param value The value
         */
        Status(String value) {
            this.value = value;
        }

        /**
         * Gets value
         *
         * @return The value
         */
        public String getValue() {
            return value;
        }
    }

    public enum Type {
        /**
         * Report type
         */
        REPORT("report"),
        /**
         * Updated type
         */
        UPDATE("update"),
        /**
         * Revision
         */
        REVISION("revision"),
        /**
         * Unknown
         */
        UNKNOWN("unknown");

        private String value;

        /**
         * Default constructor
         *
         * @param value The value
         */
        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
