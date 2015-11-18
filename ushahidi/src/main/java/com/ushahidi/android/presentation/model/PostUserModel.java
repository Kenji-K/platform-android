/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Holds the User attached to a post
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostUserModel implements Parcelable {

    /**
     * Creates {@link Parcelable} object for {@link PostUserModel}
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostUserModel> CREATOR
            = new Parcelable.Creator<PostUserModel>() {
        @Override
        public PostUserModel createFromParcel(Parcel in) {
            return new PostUserModel(in);
        }

        @Override
        public PostUserModel[] newArray(int size) {
            return new PostUserModel[size];
        }
    };

    private Long mPostId;

    private Long mUserId;

    private long mDeploymentId;

    public Long getPostId() {
        return mPostId;
    }

    public void setPostId(Long postId) {
        mPostId = postId;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public long getDeploymentId() {
        return mDeploymentId;
    }

    public void setDeploymentId(long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    public String toString() {
        return "PostUserModel{"
                + "mPostId=" + mPostId
                + ", mUserId=" + mUserId
                + ", mDeploymentId=" + mDeploymentId
                + '}';
    }

    protected PostUserModel(Parcel in) {
        mPostId = in.readByte() == 0x00 ? null : in.readLong();
        mUserId = in.readByte() == 0x00 ? null : in.readLong();
        mDeploymentId = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mPostId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mPostId);
        }
        if (mUserId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mUserId);
        }
        dest.writeLong(mDeploymentId);
    }
}