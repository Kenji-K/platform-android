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

package com.ushahidi.platform.mobile.app.data.entity.mapper;

import com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity;
import com.ushahidi.platform.mobile.app.domain.entity.Deployment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Maps {@link com.ushahidi.platform.mobile.app.domain.entity.Deployment} onto {@link
 * com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity} and vice versa
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class DeploymentEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public DeploymentEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity} to {@link Deployment}
     *
     * @param deploymentEntity The {@link DeploymentEntity} to be
     *                         mapped
     * @return The {@link Deployment} entity
     */
    public Deployment map(DeploymentEntity deploymentEntity) {
        Deployment deployment = null;

        if (deploymentEntity != null) {
            deployment = new Deployment();
            deployment._id = deploymentEntity._id;
            deployment.setStatus(Deployment.Status.valueOf(deploymentEntity.getStatus().name()));
            deployment.setTitle(deploymentEntity.getTitle());
            deployment.setUrl(deploymentEntity.getUrl());
        }

        return deployment;
    }

    /**
     * Maps {@link DeploymentEntity} onto {@link Deployment}
     *
     * @param deployment The deployment be mapped
     * @return The {@link DeploymentEntity}
     */
    public DeploymentEntity map(Deployment deployment) {
        DeploymentEntity deploymentEntity = null;

        if (deployment != null) {
            deploymentEntity = new DeploymentEntity();
            deploymentEntity._id = deployment._id;
            deploymentEntity.setTitle(deployment.getTitle());
            deploymentEntity.setStatus(DeploymentEntity.Status.valueOf(
                    deployment.getStatus().name()));
            deploymentEntity.setUrl(deployment.getUrl());
        }
        return deploymentEntity;
    }

    /**
     * Maps a list {@link DeploymentEntity} onto a list of {@link Deployment}.
     *
     * @param deploymentEntityList List to be mapped.
     * @return {@link Deployment}
     */
    public List<Deployment> map(List<DeploymentEntity> deploymentEntityList) {
        List<Deployment> deploymentList = new ArrayList<>();
        Deployment deployment;
        for (DeploymentEntity deploymentEntity : deploymentEntityList) {
            deployment = map(deploymentEntity);
            if (deployment != null) {
                deploymentList.add(deployment);
            }
        }

        return deploymentList;
    }

    /**
     * Maps a {@link com.ushahidi.platform.mobile.app.domain.entity.Deployment.Status} onto {@link
     * com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity.Status}
     *
     * @param status The status to be mapped
     * @return {@link com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity.Status}
     */
    public DeploymentEntity.Status map(Deployment.Status status) {
        return DeploymentEntity.Status.valueOf(
                status.name());
    }
}
