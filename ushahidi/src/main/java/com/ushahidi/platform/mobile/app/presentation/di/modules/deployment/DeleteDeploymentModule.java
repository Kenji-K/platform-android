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
import com.ushahidi.platform.mobile.app.domain.usecase.form.DeleteFormUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.geojson.DeleteGeoJsonUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.post.DeletePostUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.tag.DeleteTagUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.user.DeleteUserProfileUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.deployment.DeleteDeploymentUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Delete deployment dagger related module
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class DeleteDeploymentModule {

    /**
     * The default constructor
     */
    public DeleteDeploymentModule() {

    }

    /**
     * Provides {@link DeleteDeploymentUsecase} object with the annotated name "categoryDelete"
     *
     * @param listDeploymentUsecase The delete deployment use case
     * @return The delete deployment use case
     */
    @Provides
    @ActivityScope
    @Named("categoryDelete")
    DeleteDeploymentUsecase provideAddDeploymentUseCase(
            DeleteDeploymentUsecase listDeploymentUsecase) {
        return listDeploymentUsecase;
    }

    /**
     * Provides {@link DeleteFormUsecase} object with the annotated name "categoryDelete"
     *
     * @param deleteFormUsecase The delete form use case
     * @return The delete form use case
     */
    @Provides
    @ActivityScope
    @Named("categoryDelete")
    DeleteFormUsecase provideDeleteFormUseCase(
            DeleteFormUsecase deleteFormUsecase) {
        return deleteFormUsecase;
    }

    /**
     * Provides {@link DeleteGeoJsonUsecase} object with the annotated name "categoryDelete"
     *
     * @param deleteGeoJsonUsecase The delete geo json use case
     * @return The delete geo json use case
     */
    @Provides
    @ActivityScope
    @Named("categoryDelete")
    DeleteGeoJsonUsecase provideDeleteGeoJsonUseCase(
            DeleteGeoJsonUsecase deleteGeoJsonUsecase) {
        return deleteGeoJsonUsecase;
    }

    /**
     * Provides {@link DeletePostUsecase} object with the annotated name "categoryDelete"
     *
     * @param deletePostUsecase The delete post use case
     * @return The delete post use case
     */
    @Provides
    @ActivityScope
    @Named("categoryDelete")
    DeletePostUsecase provideDeletePostUseCase(
            DeletePostUsecase deletePostUsecase) {
        return deletePostUsecase;
    }

    /**
     * Provides {@link DeleteTagUsecase} object with the annotated name "categoryDelete"
     *
     * @param deleteTagUsecase The delete tag use case
     * @return The delete tag use case
     */
    @Provides
    @ActivityScope
    @Named("categoryDelete")
    DeleteTagUsecase provideDeleteTagUseCase(
            DeleteTagUsecase deleteTagUsecase) {
        return deleteTagUsecase;
    }

    /**
     * Provides {@link DeleteUserProfileUsecase} object with the annotated name "categoryDelete"
     *
     * @param deleteUserProfileUsecase The delete userproifle use case
     * @return The delete user profile use case
     */
    @Provides
    @ActivityScope
    @Named("categoryDelete")
    DeleteUserProfileUsecase provideDeleteUserProfileUseCase(
            DeleteUserProfileUsecase deleteUserProfileUsecase) {
        return deleteUserProfileUsecase;
    }

}
