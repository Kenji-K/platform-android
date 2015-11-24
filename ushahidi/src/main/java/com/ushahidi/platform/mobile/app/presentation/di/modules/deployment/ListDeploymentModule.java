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

package com.ushahidi.platform.mobile.app.presentation.di.modules.deployment;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.platform.mobile.app.domain.usecase.deployment.ListDeploymentUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * List deployment related Dagger DI modules
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class ListDeploymentModule {

    /**
     * Default constructor
     */
    public ListDeploymentModule() {
    }

    /**
     * Provides {@link ListDeploymentUsecase} object
     *
     * @param listDeploymentUsecase The list deployment use case
     * @return The list deployment use case
     */
    @Provides
    @ActivityScope
    @Named("categoryList")
    ListDeploymentUsecase provideAddDeploymentUseCase(ListDeploymentUsecase listDeploymentUsecase) {
        return listDeploymentUsecase;
    }
}
