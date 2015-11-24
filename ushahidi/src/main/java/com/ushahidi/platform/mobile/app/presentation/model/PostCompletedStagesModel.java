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
 * The post completed stages entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostCompletedStagesModel implements Parcelable {

    /**
     * Creates {@link Parcelable} object for {@link PostCompletedStagesModel}
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostCompletedStagesModel> CREATOR
            = new Parcelable.Creator<PostCompletedStagesModel>() {
        @Override
        public PostCompletedStagesModel createFromParcel(Parcel in) {
            return new PostCompletedStagesModel(in);
        }

        @Override
        public PostCompletedStagesModel[] newArray(int size) {
            return new PostCompletedStagesModel[size];
        }
    };

    private String mCompletedStep;

    public PostCompletedStagesModel() {

    }

    protected PostCompletedStagesModel(Parcel in) {
        mCompletedStep = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCompletedStep);
    }

    public String getCompletedStep() {
        return mCompletedStep;
    }

    public void setCompletedStep(String completedStep) {
        mCompletedStep = completedStep;
    }

    @Override
    public String toString() {
        return "PostCompletedStagesModel{"
                + "mCompletedStep='" + mCompletedStep + '\''
                + '}';
    }
}