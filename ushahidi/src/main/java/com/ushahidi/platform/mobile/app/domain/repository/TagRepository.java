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
import com.ushahidi.platform.mobile.app.domain.entity.Tag;

import java.util.List;

import rx.Observable;

/**
 * Repository for Tag
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface TagRepository {

    /**
     * Get an {@link Observable} that a emits a list of {@link Tag}
     *
     * @param deploymentId @param deploymentId The deploymentId to be used for fetching the API
     * @param from         Where to fetch the deployment from. Either Online or Offline.
     * @return List of tags
     */
    Observable<List<Tag>> getTagList(Long deploymentId, From from);

    /**
     * Add / Update an {@link Tag} to a storage.
     *
     * @param entity The entity to be added.
     * @return The row affected
     */
    Observable<Long> putTag(List<Tag> entity);

    /**
     * Delete an existing {@link Tag} in a storage.
     *
     * @param tag The tag to be deleted.
     * @return True if successfully deleted otherwise false
     */
    Observable<Boolean> deleteTag(Tag tag);

    /**
     * Deletes all items with the supplied deployment id
     *
     * @param deploymentId The deployment id
     * @return True if successfully deleted otherwise false
     */
    Observable<Boolean> deleteTagList(Long deploymentId);

}
