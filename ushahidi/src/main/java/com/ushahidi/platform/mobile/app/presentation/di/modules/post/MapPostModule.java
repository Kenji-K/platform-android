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

package com.ushahidi.platform.mobile.app.presentation.di.modules.post;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.platform.mobile.app.domain.usecase.geojson.ListGeoJsonUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Provides injectable modules scoped with {@link ActivityScope} to map post related classes
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class MapPostModule {

    /**
     * Provides {@link ListGeoJsonUsecase} object annotated with the name "mapList"
     *
     * @param listGeoJsonUsecase The list GeoJson use case
     * @return The list geo json use case
     */
    @Provides
    @ActivityScope
    @Named("mapList")
    ListGeoJsonUsecase provideListGeoJsonUsecase(ListGeoJsonUsecase listGeoJsonUsecase) {
        return listGeoJsonUsecase;
    }
}
