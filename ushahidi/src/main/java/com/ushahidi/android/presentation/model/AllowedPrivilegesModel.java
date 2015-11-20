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
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AllowedPrivilegesModel implements Parcelable {

    /**
     * Creates {@link Parcelable} object for {@link AllowedPrivilegesModel}
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AllowedPrivilegesModel> CREATOR
            = new Parcelable.Creator<AllowedPrivilegesModel>() {
        @Override
        public AllowedPrivilegesModel createFromParcel(Parcel in) {
            return new AllowedPrivilegesModel(in);
        }

        @Override
        public AllowedPrivilegesModel[] newArray(int size) {
            return new AllowedPrivilegesModel[size];
        }
    };

    private String mAllowedPrivileges;

    public AllowedPrivilegesModel() {

    }

    protected AllowedPrivilegesModel(Parcel in) {
        mAllowedPrivileges = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAllowedPrivileges);
    }

    public String getAllowedPrivileges() {
        return mAllowedPrivileges;
    }

    public void setAllowedPrivileges(String allowedPrivileges) {
        mAllowedPrivileges = allowedPrivileges;
    }

    @Override
    public String toString() {
        return "AllowedPrivilegesModel{"
                + "mAllowedPrivileges='" + mAllowedPrivileges + '\''
                + '}';
    }

}