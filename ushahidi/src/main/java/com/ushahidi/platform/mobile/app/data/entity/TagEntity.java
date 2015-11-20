/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.platform.mobile.app.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import nl.qbusict.cupboard.annotation.Ignore;

/**
 * Tag data entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagEntity extends Data {

    @SerializedName("parent")
    @Ignore // Make cupboard ignore this field
    //For some reasons gson expects this field otherwise it throws errors
    private Parent parent;

    private transient Long mParent;

    @SerializedName("tag")
    private String mTag;

    @SerializedName("color")
    private String mColor;

    @SerializedName("type")
    private Type mType;

    @SerializedName("icon")
    private String mIcon;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("priority")
    private int mPriority;

    @SerializedName("created")
    private Date mCreated;

    private Long mDeploymentId;

    public Long getParentId() {
        return mParent;
    }

    public void setParentId(Long parentId) {
        mParent = parentId;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        mCreated = created;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

    public long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "Tag{"
                + "mParent=" + mParent
                + ", mTag='" + mTag + '\''
                + ", mId='" + _id + '\''
                + ", mColor='" + mColor + '\''
                + ", mType=" + mType
                + ", mIcon='" + mIcon + '\''
                + ", mDescription='" + mDescription + '\''
                + ", mPriority=" + mPriority
                + ", mCreated=" + mCreated
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }

    /**
     * Represents the tag entity type
     */
    public enum Type {
        /**
         * The category
         */
        @SerializedName("category")
        CATEGORY("category"),

        /**
         * The status
         */
        @SerializedName("status")
        STATUS("status");

        private final String value;

        /**
         * Default constructor
         *
         * @param value The value
         */
        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * The parent property of the tag
     */
    public static class Parent {

        @SerializedName("id")
        private Long id;

        public Long getId() {
            return id;
        }
    }
}
