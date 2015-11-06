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

package com.ushahidi.android.data.repository.datasource.post;

import com.ushahidi.android.data.entity.PostEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface PostDataSource {

    /**
     * Add/Update a {@link com.ushahidi.android.data.entity.PostEntity}.
     *
     * @param postEntities The post entities to be saved.
     * @return The row affected
     */
    Observable<Long> putPostEntities(List<PostEntity> postEntities);

    /**
     * Add/Update a {@link com.ushahidi.android.data.entity.PostEntity}.
     *
     * @param postEntity The post entity to be saved.
     * @return The row affected
     */
    Observable<Long> putPostEntity(PostEntity postEntity);

    /**
     * Get a list of {@link com.ushahidi.android.data.entity.PostEntity}.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.data.entity.PostEntity}
     * @return The list of post entities
     */
    Observable<List<PostEntity>> getPostEntityList(Long deploymentId);

    /**
     * Get an {@link com.ushahidi.android.data.entity.PostEntity} by id.
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.data.entity.PostEntity}
     * @param postEntityId The post entity id used for retrieving post data.
     * @return The post entity
     */
    Observable<PostEntity> getPostEntityById(Long deploymentId, Long postEntityId);

    /**
     * Delete a {@link com.ushahidi.android.data.entity.PostEntity}
     *
     * @param postEntity The post to be deleted.
     * @return True if post entity is successfully deleted otherwise false
     */
    Observable<Boolean> deletePostEntity(PostEntity postEntity);

    /**
     * Search for a {@link com.ushahidi.android.data.entity.PostEntity}
     *
     * @param deploymentId An ID of {@link com.ushahidi.android.data.entity.DeploymentEntity}
     * @param query        The search query.
     * @return A list of post entities
     */
    Observable<List<PostEntity>> search(Long deploymentId, String query);

    /**
     * Deletes all posts with the supplied deployment id
     *
     * @param deploymentId The deployment id
     * @return True upon successful deletion, otherwise false
     */
    Observable<Boolean> deleteDeploymentPosts(Long deploymentId);

}
