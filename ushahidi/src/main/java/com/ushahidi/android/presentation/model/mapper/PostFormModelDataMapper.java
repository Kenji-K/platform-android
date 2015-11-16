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

import com.ushahidi.android.domain.entity.PostForm;
import com.ushahidi.android.presentation.model.PostFormModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostFormModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public PostFormModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link PostFormModel} onto {@link PostForm}
     *
     * @param postFormModel The post entity to be mapped
     * @return The post value
     */
    public PostForm map(PostFormModel postFormModel) {
        PostForm postForm = null;
        if (postFormModel != null) {
            postForm = new PostForm();
            postForm.setDeploymentId(postFormModel.getDeploymentId());
            postForm.setPostId(postFormModel.getPostId());
            postForm.setFormId(postFormModel.getFormId());
        }
        return postForm;
    }

    /**
     * Maps {@link PostForm} onto {@link PostFormModel}
     *
     * @param postForm The post form entity
     * @return The post form
     */
    public PostFormModel map(PostForm postForm) {
        PostFormModel postFormModel = null;
        if (postForm != null) {
            postFormModel = new PostFormModel();
            postFormModel.setDeploymentId(postForm.getDeploymentId());
            postFormModel.setPostId(postForm.getPostId());
            postFormModel.setFormId(postForm.getFormId());
        }
        return postFormModel;
    }

    /**
     * Maps a list {@link PostFormModel} into a list of {@link PostForm}.
     *
     * @param postModelList List to be mapped.
     * @return {@link PostForm}
     */
    public List<PostForm> map(List<PostFormModel> postModelList) {
        List<PostForm> postFormList = new ArrayList<>();
        PostForm post;
        for (PostFormModel postFormModel : postModelList) {
            post = map(postFormModel);
            if (post != null) {
                postFormList.add(post);
            }
        }
        return postFormList;
    }
}
