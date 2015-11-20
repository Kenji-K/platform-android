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

package com.ushahidi.platform.mobile.app.data.repository.datasource.geojson;

import com.ushahidi.platform.mobile.app.data.api.GeoJsonApi;
import com.ushahidi.platform.mobile.app.data.api.oauth.UshAccessTokenManager;
import com.ushahidi.platform.mobile.app.data.database.GeoJsonDatabaseHelper;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * For creating the object of the various data source for {@link com.ushahidi.platform.mobile.app.data.entity.GeoJsonEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class GeoJsonDataSourceFactory {

    private final GeoJsonDatabaseHelper mGeoDatabaseHelper;

    private final UshAccessTokenManager mUshAccessTokenManager;

    /**
     * Default constructor
     *
     * @param geoJsonDatabaseHelper The geojson database helper
     * @param ushAccessTokenManager The access token manager
     */
    @Inject
    public GeoJsonDataSourceFactory(
            @NonNull GeoJsonDatabaseHelper geoJsonDatabaseHelper, @NonNull UshAccessTokenManager
            ushAccessTokenManager) {
        mGeoDatabaseHelper = geoJsonDatabaseHelper;
        mUshAccessTokenManager = ushAccessTokenManager;
    }

    /**
     * Creates a {@link GeoJsonApiDataSource}
     *
     * @return The geojson api data source
     */
    public GeoJsonDataSource createGeoJsonApiDataSource() {
        final GeoJsonApi geoJsonApi = new GeoJsonApi(mUshAccessTokenManager);
        return new GeoJsonApiDataSource(geoJsonApi, mGeoDatabaseHelper);
    }

    /**
     * Creates {@link GeoJsonDatabaseDataSource}
     *
     * @return The geojson data source
     */
    public GeoJsonDataSource createGeoJsonDatabaseDataSource() {
        return new GeoJsonDatabaseDataSource(mGeoDatabaseHelper);
    }
}
