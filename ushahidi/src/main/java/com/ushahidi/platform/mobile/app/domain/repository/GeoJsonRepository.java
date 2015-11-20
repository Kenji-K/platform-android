/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.platform.mobile.app.domain.repository;

import com.ushahidi.platform.mobile.app.domain.entity.From;
import com.ushahidi.platform.mobile.app.domain.entity.GeoJson;

import rx.Observable;

/**
 * GeoJson Repository
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface GeoJsonRepository {

    /**
     * Get a list of {@link GeoJson} from either the database or online.
     *
     * @param deploymentId An ID of {@link com.ushahidi.platform.mobile.app.domain.entity.Deployment}
     * @param from         Where to get the geojson from
     * @return The deployment
     */
    Observable<GeoJson> getGeoJson(Long deploymentId, From from);

    /**
     * Add/Update a {@link GeoJson}.
     *
     * @param geoJson The GeoJson to be saved.
     * @return The row affected
     */
    Observable<Long> putGeoJson(GeoJson geoJson);
}
