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

package com.ushahidi.platform.mobile.app.data.api;

import com.google.gson.JsonElement;

import com.ushahidi.platform.mobile.app.data.api.model.FormAttributes;
import com.ushahidi.platform.mobile.app.data.api.model.FormStages;
import com.ushahidi.platform.mobile.app.data.api.model.Forms;
import com.ushahidi.platform.mobile.app.data.api.model.Posts;
import com.ushahidi.platform.mobile.app.data.api.model.Tags;
import com.ushahidi.platform.mobile.app.data.api.oauth.UshAccessTokenManager;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Observable;

/**
 * Provides post related API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostApi {

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor
     *
     * @param ushAccessTokenManager The access token manager. Cannot be a null value
     */
    @Inject
    public PostApi(@NonNull UshAccessTokenManager ushAccessTokenManager) {
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Gets a {@link Posts}
     *
     * @return An Observable that emits {@link Posts}
     */
    public Observable<Posts> getPostList() {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .posts(authorizationHeader));
    }

    /**
     * Gets a {@link Tags}
     *
     * @return An Observable that emits {@link Tags}
     */
    public Observable<Tags> getTags() {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .getTags(authorizationHeader)
        );
    }

    /**
     * Gets a {@link JsonElement}
     *
     * @return An Observable that emits {@link JsonElement}
     */
    public Observable<JsonElement> getGeoJson() {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .getGeoJson(authorizationHeader));
    }

    /**
     * Gets a {@link Forms}
     *
     * @return An Observable that emits{@link Forms}
     */
    public Observable<Forms> getForms() {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .getForms(authorizationHeader));
    }

    public Observable<FormAttributes> getFormAttributes() {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService()
                        .getFormAttributes(authorizationHeader));
    }

    public Observable<FormStages> getFormStages() {
        return mUshAccessTokenManager.getValidAccessToken().concatMap(
                authorizationHeader -> mUshAccessTokenManager.getRestfulService().getFormStages(
                        authorizationHeader));
    }
}