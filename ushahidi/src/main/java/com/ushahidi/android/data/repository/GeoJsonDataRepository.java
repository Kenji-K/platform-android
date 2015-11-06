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

package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.entity.mapper.GeoJsonEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.geojson.GeoJsonDataSource;
import com.ushahidi.android.data.repository.datasource.geojson.GeoJsonDataSourceFactory;
import com.ushahidi.android.domain.entity.From;
import com.ushahidi.android.domain.entity.GeoJson;
import com.ushahidi.android.domain.repository.GeoJsonRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Implements {@link com.ushahidi.android.domain.repository.GeoJsonRepository} to manipulate data
 * related to GeoJson.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class GeoJsonDataRepository implements GeoJsonRepository {

    private final GeoJsonEntityDataMapper mGeoJsonEntityDataMapper;

    private final GeoJsonDataSourceFactory mGeoJsonDataSourceFactory;

    /**
     * Default constructor that constructs {@link GeoJsonRepository}
     *
     * @param geoJsonDataSourceFactory The geojson data source factory
     * @param geoJsonEntityDataMapper  The geojson entity data mapper
     */
    @Inject
    public GeoJsonDataRepository(GeoJsonDataSourceFactory geoJsonDataSourceFactory,
            GeoJsonEntityDataMapper geoJsonEntityDataMapper) {
        mGeoJsonDataSourceFactory = geoJsonDataSourceFactory;
        mGeoJsonEntityDataMapper = geoJsonEntityDataMapper;
    }

    @Override
    public Observable<GeoJson> getGeoJson(Long deploymentId, From from) {
        GeoJsonDataSource geoJsonDataSource;
        if (from.equals(From.ONLINE)) {
            geoJsonDataSource = mGeoJsonDataSourceFactory.createGeoJsonApiDataSource();
        } else {
            geoJsonDataSource = mGeoJsonDataSourceFactory
                    .createGeoJsonDatabaseDataSource();
        }
        return geoJsonDataSource.getGeoJsonList(deploymentId)
                .map(mGeoJsonEntityDataMapper::map);
    }

    @Override
    public Observable<Long> putGeoJson(GeoJson geoJson) {
        final GeoJsonDataSource geoJsonDataSource = mGeoJsonDataSourceFactory
                .createGeoJsonDatabaseDataSource();
        return geoJsonDataSource.putGeoJson(mGeoJsonEntityDataMapper.map(geoJson));
    }

    @Override
    public Observable<Boolean> deleteGeoJsonList(Long deploymentId) {
        final GeoJsonDataSource geoJsonDataSource = mGeoJsonDataSourceFactory
                .createGeoJsonDatabaseDataSource();
        return geoJsonDataSource.deleteGeoJsonList(deploymentId);
    }

}
