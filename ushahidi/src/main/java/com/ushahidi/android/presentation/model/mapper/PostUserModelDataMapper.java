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

import com.ushahidi.android.data.entity.PostUserEntity;
import com.ushahidi.android.domain.entity.PostUser;
import com.ushahidi.android.presentation.model.PostUserModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PostUserModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public PostUserModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link PostUserEntity} onto {@link PostUser}
     *
     * @param postUserModel The post entity to be mapped
     * @return The post value
     */
    public PostUser map(PostUserModel postUserModel) {
        PostUser postUser = null;
        if (postUserModel != null) {
            postUser = new PostUser();
            postUser.setDeploymentId(postUserModel.getDeploymentId());
            postUser.setPostId(postUserModel.getPostId());
            postUser.setUserId(postUserModel.getUserId());
        }
        return postUser;
    }

    /**
     * Maps {@link PostUser} onto {@link PostUserEntity}
     *
     * @param postUser The post form entity
     * @return The post form
     */
    public PostUserModel map(PostUser postUser) {
        PostUserModel postUserModel = null;
        if (postUser != null) {
            postUserModel = new PostUserModel();
            postUserModel.setDeploymentId(postUser.getDeploymentId());
            postUserModel.setPostId(postUser.getPostId());
            postUserModel.setUserId(postUser.getUserId());
        }
        return postUserModel;
    }

    /**
     * Maps a list {@link PostUserEntity} into a list of {@link PostUser}.
     *
     * @param postModelList List to be mapped.
     * @return {@link PostUser}
     */
    public List<PostUser> map(List<PostUserModel> postModelList) {
        List<PostUser> postUserList = new ArrayList<>();
        PostUser post;
        for (PostUserModel postUserModel : postModelList) {
            post = map(postUserModel);
            if (post != null) {
                postUserList.add(post);
            }
        }
        return postUserList;
    }
}
