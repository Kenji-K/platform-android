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

import com.ushahidi.platform.mobile.app.data.entity.PostUserEntity;
import com.ushahidi.platform.mobile.app.domain.entity.PostUser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class PostUserEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public PostUserEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link PostUserEntity} onto {@link PostUser}
     *
     * @param postUserEntity The post entity to be mapped
     * @return The post value
     */
    public PostUser map(PostUserEntity postUserEntity) {
        PostUser postUser = null;
        if (postUser != null) {
            postUser = new PostUser();
            postUser.setDeploymentId(postUserEntity.getDeploymentId());
            postUser.setPostId(postUserEntity.getPostId());
            postUser.setUserId(postUserEntity.getUserId());
        }
        return postUser;
    }

    /**
     * Maps {@link PostUser} onto {@link PostUserEntity}
     *
     * @param postUser The post form entity
     * @return The post form
     */
    public PostUserEntity map(PostUser postUser) {
        PostUserEntity postUserEntity = null;
        if (postUser != null) {
            postUserEntity = new PostUserEntity();
            postUserEntity.setDeploymentId(postUser.getDeploymentId());
            postUserEntity.setPostId(postUser.getPostId());
            postUserEntity.setUserId(postUser.getUserId());
        }
        return postUserEntity;
    }

    /**
     * Maps a list {@link PostUserEntity} into a list of {@link PostUser}.
     *
     * @param postEntityList List to be mapped.
     * @return {@link PostUser}
     */
    public List<PostUser> map(List<PostUserEntity> postEntityList) {
        List<PostUser> postUserList = new ArrayList<>();
        PostUser post;
        for (PostUserEntity postUserEntity : postEntityList) {
            post = map(postUserEntity);
            if (post != null) {
                postUserList.add(post);
            }
        }
        return postUserList;
    }
}
