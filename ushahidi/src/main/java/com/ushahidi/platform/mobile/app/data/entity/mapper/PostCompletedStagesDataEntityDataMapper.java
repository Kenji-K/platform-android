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

package com.ushahidi.platform.mobile.app.data.entity.mapper;

import com.ushahidi.platform.mobile.app.data.entity.PostCompletedStagesEntity;
import com.ushahidi.platform.mobile.app.domain.entity.PostCompletedStages;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PostCompletedStagesDataEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public PostCompletedStagesDataEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link PostCompletedStagesEntity} onto {@link PostCompletedStages}
     *
     * @param postCompletedStagesEntity The post entity to be mapped
     * @return The post value
     */
    public PostCompletedStages map(PostCompletedStagesEntity postCompletedStagesEntity) {
        PostCompletedStages postCompletedStages = null;
        if (postCompletedStagesEntity != null) {
            postCompletedStages = new PostCompletedStages();
            postCompletedStages.setCompletedStep(postCompletedStagesEntity.getCompletedStep());
        }
        return postCompletedStages;
    }

    /**
     * Maps {@link PostCompletedStages} onto {@link PostCompletedStagesEntity}
     *
     * @param postCompletedStages The post value entity
     * @return The post value
     */
    public PostCompletedStagesEntity map(PostCompletedStages postCompletedStages) {
        PostCompletedStagesEntity postCompletedStagesEntity = null;
        if (postCompletedStages != null) {
            postCompletedStagesEntity = new PostCompletedStagesEntity();
            postCompletedStagesEntity.setCompletedStep(postCompletedStages.getCompletedStep());
        }
        return postCompletedStagesEntity;
    }

    /**
     * Maps a list {@link PostCompletedStagesEntity} into a list of {@link PostCompletedStages}.
     *
     * @param postCompletedStagesEntityList List to be mapped.
     * @return {@link PostCompletedStages}
     */
    public List<PostCompletedStages> map(
            List<PostCompletedStagesEntity> postCompletedStagesEntityList) {
        List<PostCompletedStages> postCompletedStagesList = new ArrayList<>();
        PostCompletedStages post;
        for (PostCompletedStagesEntity postCompletedStagesEntity : postCompletedStagesEntityList) {
            post = map(postCompletedStagesEntity);
            if (post != null) {
                postCompletedStagesList.add(post);
            }
        }
        return postCompletedStagesList;
    }
}
