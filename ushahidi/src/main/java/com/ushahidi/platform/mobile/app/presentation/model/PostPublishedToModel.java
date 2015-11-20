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

package com.ushahidi.platform.mobile.app.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostPublishedToModel implements Parcelable {

    /**
     * Creates a {@link Parcelable} object for {@link PostPublishedToModel}
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostPublishedToModel> CREATOR
            = new Parcelable.Creator<PostPublishedToModel>() {
        @Override
        public PostPublishedToModel createFromParcel(Parcel in) {
            return new PostPublishedToModel(in);
        }

        @Override
        public PostPublishedToModel[] newArray(int size) {
            return new PostPublishedToModel[size];
        }
    };

    private String mPublishedTo;

    protected PostPublishedToModel(Parcel in) {
        mPublishedTo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPublishedTo);
    }

    public String getPublishedTo() {
        return mPublishedTo;
    }

    public void setPublishedTo(String publishedTo) {
        mPublishedTo = publishedTo;
    }

    @Override
    public String toString() {
        return "PostPublishedToModel{"
                + "mPublishedTo='" + mPublishedTo + '\''
                + '}';
    }
}
