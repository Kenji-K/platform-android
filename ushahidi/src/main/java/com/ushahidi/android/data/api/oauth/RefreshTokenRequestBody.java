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

package com.ushahidi.android.data.api.oauth;

import com.google.gson.annotations.SerializedName;

import com.ushahidi.android.data.api.qualifier.ClientId;
import com.ushahidi.android.data.api.qualifier.ClientSecret;
import com.ushahidi.android.data.api.qualifier.GrantType;

import java.io.Serializable;

/**
 * Body object used to exchange a code with an access token.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class RefreshTokenRequestBody implements Serializable {

    @SerializedName("grant_type")
    private final String mGrantType;

    @SerializedName("client_id")
    private final String mClientId;

    @SerializedName("client_secret")
    private final String mClientSecret;

    @SerializedName("refresh_token")
    private String mRefreshToken;

    /**
     * Default constructor
     *
     * @param refreshToken The refresh token
     * @param grantType    The grant type
     * @param clientId     The client ID
     * @param clientSecret The client secret
     */
    public RefreshTokenRequestBody(String refreshToken, @GrantType String grantType,
            @ClientId String clientId, @ClientSecret String clientSecret) {
        mRefreshToken = refreshToken;
        mGrantType = grantType;
        mClientId = clientId;
        mClientSecret = clientSecret;
    }

    public String getGrantType() {
        return mGrantType;
    }

    public String getClientId() {
        return mClientId;
    }

    public String getClientSecret() {
        return mClientSecret;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequestBody{"
                + "grantType='" + mGrantType + '\''
                + ", clientId='" + mClientId + '\''
                + ", clientSecret='" + mClientSecret + '\''
                + ", refreshToken='" + mRefreshToken + '\''
                + '}';
    }
}
