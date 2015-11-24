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

package com.ushahidi.platform.mobile.app.data.repository.datasource.deployment;

import android.support.annotation.NonNull;

import com.ushahidi.platform.mobile.app.data.api.DeploymentApi;
import com.ushahidi.platform.mobile.app.data.database.DeploymentDatabaseHelper;
import com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentApiDataSource implements DeploymentDataSource {

    private final DeploymentApi mDeploymentApi;

    private final DeploymentDatabaseHelper mDeploymentDatabaseHelper;

    /**
     * Default constructor that constructs {@link DeploymentApiDataSource}
     *
     * @param deploymentApi            The Deployment API
     * @param deploymentDatabaseHelper The Deployment database helper
     */
    public DeploymentApiDataSource(@NonNull DeploymentApi deploymentApi,
                                DeploymentDatabaseHelper deploymentDatabaseHelper) {
        mDeploymentApi = deploymentApi;
        mDeploymentDatabaseHelper = deploymentDatabaseHelper;
    }

    @Override
    public Observable<List<DeploymentEntity>> getDeploymentEntityList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<DeploymentEntity> getDeploymentEntity(Long deploymentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<DeploymentEntity> getByStatus(DeploymentEntity.Status status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Long> addDeploymentEntity(DeploymentEntity deployment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Long> updateDeploymentEntity(DeploymentEntity deployment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Long> deleteDeploymentEntity(Long deploymentId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<DeploymentEntity> getDeploymentEntity(String url) {
        return mDeploymentApi.getDeploymentConfig(url).doOnNext(deploymentEntity -> mDeploymentDatabaseHelper
                .put(deploymentEntity));
    }

}
