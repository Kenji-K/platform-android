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

import com.ushahidi.android.data.entity.PostFormEntity;
import com.ushahidi.android.domain.entity.PostForm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PostFormModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public PostFormModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link PostFormEntity} onto {@link PostForm}
     *
     * @param postFormEntity The post entity to be mapped
     * @return The post value
     */
    public PostForm map(PostFormEntity postFormEntity) {
        PostForm postForm = null;
        if (postForm != null) {
            postForm = new PostForm();
            postForm.setDeploymentId(postFormEntity.getDeploymentId());
            postForm.setPostId(postFormEntity.getPostId());
            postForm.setFormId(postFormEntity.getFormId());
        }
        return postForm;
    }

    /**
     * Maps {@link PostForm} onto {@link PostFormEntity}
     *
     * @param postForm The post form entity
     * @return The post form
     */
    public PostFormEntity map(PostForm postForm) {
        PostFormEntity postFormEntity = null;
        if (postForm != null) {
            postFormEntity = new PostFormEntity();
            postFormEntity.setDeploymentId(postForm.getDeploymentId());
            postFormEntity.setPostId(postForm.getPostId());
            postFormEntity.setFormId(postForm.getFormId());
        }
        return postFormEntity;
    }

    /**
     * Maps a list {@link PostFormEntity} into a list of {@link PostForm}.
     *
     * @param postEntityList List to be mapped.
     * @return {@link PostForm}
     */
    public List<PostForm> map(List<PostFormEntity> postEntityList) {
        List<PostForm> postFormList = new ArrayList<>();
        PostForm post;
        for (PostFormEntity postFormEntity : postEntityList) {
            post = map(postFormEntity);
            if (post != null) {
                postFormList.add(post);
            }
        }
        return postFormList;
    }
}
