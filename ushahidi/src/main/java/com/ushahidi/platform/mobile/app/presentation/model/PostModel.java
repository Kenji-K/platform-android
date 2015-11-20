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

package com.ushahidi.platform.mobile.app.presentation.model;

import com.google.gson.annotations.SerializedName;

import com.addhen.android.raiburari.presentation.model.Model;
import com.ushahidi.platform.mobile.app.domain.entity.AllowedPrivileges;
import com.ushahidi.platform.mobile.app.domain.entity.PostCompletedStages;
import com.ushahidi.platform.mobile.app.domain.entity.PostPublishedTo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Post model
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostModel extends Model implements Parcelable {

    /**
     * Creates a {@link PostModel} parcelable object
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostModel> CREATOR
            = new Parcelable.Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    private Parent parent;

    private transient Long mParent;

    private Type mType;

    private String mTitle;

    private String mSlug;

    private String mContent;

    private String mAuthorEmail;

    private String mAuthorRealname;

    private Status mStatus;

    private Date mCreated;

    private Date mUpdated;

    private PostValueModel mValues;

    private List<PostTagModel> mPostTagEntityList;

    private long mDeploymentId;

    private transient List<TagModel> mTags;

    private PostFormModel mPostForm;

    private PostCompletedStages mCompletedStages;

    private AllowedPrivileges mAllowedPrivileges;

    private PostUserModel mPostUser;

    private PostPublishedTo mPostPublishedTo;

    /**
     * Default constructor
     */
    public PostModel() {
    }

    protected PostModel(Parcel in) {
        parent = (Parent) in.readValue(Parent.class.getClassLoader());
        mParent = in.readByte() == 0x00 ? null : in.readLong();
        mType = (Type) in.readValue(Type.class.getClassLoader());
        mTitle = in.readString();
        mSlug = in.readString();
        mContent = in.readString();
        mAuthorEmail = in.readString();
        mAuthorRealname = in.readString();
        mStatus = (Status) in.readValue(Status.class.getClassLoader());
        long tmpMCreated = in.readLong();
        mCreated = tmpMCreated != -1 ? new Date(tmpMCreated) : null;
        long tmpMUpdated = in.readLong();
        mUpdated = tmpMUpdated != -1 ? new Date(tmpMUpdated) : null;
        mValues = (PostValueModel) in.readValue(PostValueModel.class.getClassLoader());
        if (in.readByte() == 0x01) {
            mPostTagEntityList = new ArrayList<PostTagModel>();
            in.readList(mPostTagEntityList, PostTagModel.class.getClassLoader());
        } else {
            mPostTagEntityList = null;
        }
        mDeploymentId = in.readLong();
        if (in.readByte() == 0x01) {
            mTags = new ArrayList<TagModel>();
            in.readList(mTags, TagModel.class.getClassLoader());
        } else {
            mTags = null;
        }
        mPostForm = (PostFormModel) in.readValue(PostFormModel.class.getClassLoader());
        mCompletedStages = (PostCompletedStages) in
                .readValue(PostCompletedStages.class.getClassLoader());
        mAllowedPrivileges = (AllowedPrivileges) in.readValue(
                AllowedPrivileges.class.getClassLoader());
        mPostUser = (PostUserModel) in.readValue(PostUserModel.class.getClassLoader());
        mPostPublishedTo = (PostPublishedTo) in.readValue(PostPublishedTo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(parent);
        if (mParent == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mParent);
        }
        dest.writeValue(mType);
        dest.writeString(mTitle);
        dest.writeString(mSlug);
        dest.writeString(mContent);
        dest.writeString(mAuthorEmail);
        dest.writeString(mAuthorRealname);
        dest.writeValue(mStatus);
        dest.writeLong(mCreated != null ? mCreated.getTime() : -1L);
        dest.writeLong(mUpdated != null ? mUpdated.getTime() : -1L);
        dest.writeValue(mValues);
        if (mPostTagEntityList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mPostTagEntityList);
        }
        dest.writeLong(mDeploymentId);
        if (mTags == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mTags);
        }
        dest.writeValue(mPostForm);
        dest.writeValue(mCompletedStages);
        dest.writeValue(mAllowedPrivileges);
        dest.writeValue(mPostUser);
        dest.writeValue(mPostPublishedTo);
    }

    public PostFormModel getPostForm() {
        return mPostForm;
    }

    public void setPostForm(PostFormModel postForm) {
        mPostForm = postForm;
    }

    public void setPostUser(PostUserModel postUser) {
        mPostUser = postUser;
    }

    public Parent getParent() {
        return parent;
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

    public String getAuthorEmail() {
        return mAuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        mAuthorEmail = authorEmail;
    }

    public String getAuthorRealname() {
        return mAuthorRealname;
    }

    public void setAuthorRealname(String authorRealname) {
        mAuthorRealname = authorRealname;
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

    public PostValueModel getValues() {
        return mValues;
    }

    public void setValues(PostValueModel values) {
        mValues = values;
    }

    public List<PostTagModel> getPostTagEntityList() {
        return mPostTagEntityList;
    }

    public void setPostTagEntityList(
            List<PostTagModel> postTagEntityList) {
        mPostTagEntityList = postTagEntityList;
    }

    public long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(long deploymentId) {
        mDeploymentId = deploymentId;
    }

    public List<TagModel> getTags() {
        return mTags;
    }

    public void setTags(List<TagModel> tags) {
        mTags = tags;
    }

    public PostCompletedStages getCompletedStages() {
        return mCompletedStages;
    }

    public void setCompletedStages(
            PostCompletedStages completedStages) {
        mCompletedStages = completedStages;
    }

    public PostFormModel getPostFormModel() {
        return mPostForm;
    }

    public void setFormModel(PostFormModel postForm) {
        mPostForm = postForm;
    }

    public AllowedPrivileges getAllowedPrivileges() {
        return mAllowedPrivileges;
    }

    public void setAllowedPrivileges(
            AllowedPrivileges allowedPrivileges) {
        mAllowedPrivileges = allowedPrivileges;
    }

    public PostUserModel getPostUser() {
        return mPostUser;
    }

    public void setPostUserModel(PostUserModel postUser) {
        mPostUser = postUser;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public PostPublishedTo getPostPublishedTo() {
        return mPostPublishedTo;
    }

    public void setPostPublishedTo(PostPublishedTo postPublishedTo) {
        mPostPublishedTo = postPublishedTo;
    }

    public enum Status {
        /**
         * A draft status
         */
        @SerializedName("draft")
        DRAFT("draft"),

        /**
         * A published status
         */
        @SerializedName("published")
        PUBLISHED("published"),

        /**
         * A pending status
         */
        @SerializedName("pending")
        PENDING("pending"),

        /**
         * An unknown status
         */
        @SerializedName("unknown")
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


    /**
     * Represents the parent property of the a post
     */
    public static class Parent {

        private Long id;

        public Long getId() {
            return id;
        }
    }
}