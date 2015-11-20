/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.model.mapper;

import com.ushahidi.android.data.entity.PostPublishedToEntity;
import com.ushahidi.android.domain.entity.PostPublishedTo;
import com.ushahidi.android.presentation.model.PostPublishedToModel;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostPublishedToModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public PostPublishedToModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link PostPublishedToEntity} onto {@link PostPublishedTo}
     *
     * @param postPublishedToEntity The post entity to be mapped
     * @return The post value
     */
    public PostPublishedTo map(PostPublishedToEntity postPublishedToEntity) {
        PostPublishedTo postPublishedTo = null;
        if (postPublishedToEntity != null) {
            postPublishedTo = new PostPublishedTo();
            postPublishedTo.setPublishedTo(postPublishedToEntity.getPublishedTo());
        }
        return postPublishedTo;
    }

    /**
     * Maps {@link PostPublishedTo} onto {@link PostPublishedToEntity}
     *
     * @param postPublishedToModel The post value entity
     * @return The post value
     */
    public PostPublishedTo map(PostPublishedToModel postPublishedToModel) {
        PostPublishedTo postPublishedTo = null;
        if (postPublishedToModel != null) {
            postPublishedTo = new PostPublishedTo();
            postPublishedTo.setPublishedTo(postPublishedToModel.getPublishedTo());
        }
        return postPublishedTo;
    }
}
