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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.PostPublishedToEntity;
import com.ushahidi.android.domain.entity.PostPublishedTo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PostPublishedToEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public PostPublishedToEntityDataMapper() {
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
     * @param postPublishedTo The post value entity
     * @return The post value
     */
    public PostPublishedToEntity map(PostPublishedTo postPublishedTo) {
        PostPublishedToEntity postPublishedToEntity = null;
        if (postPublishedTo != null) {
            postPublishedToEntity = new PostPublishedToEntity();
            postPublishedToEntity.setPublishedTo(postPublishedTo.getPublishedTo());
        }
        return postPublishedToEntity;
    }

    /**
     * Maps a list {@link PostPublishedToEntity} into a list of {@link PostPublishedTo}.
     *
     * @param postEntityList List to be mapped.
     * @return {@link PostPublishedTo}
     */
    public List<PostPublishedTo> map(List<PostPublishedToEntity> postEntityList) {
        List<PostPublishedTo> postPublishedToList = new ArrayList<>();
        PostPublishedTo post;
        for (PostPublishedToEntity postPublishedToEntity : postEntityList) {
            post = map(postPublishedToEntity);
            if (post != null) {
                postPublishedToList.add(post);
            }
        }
        return postPublishedToList;
    }
}
