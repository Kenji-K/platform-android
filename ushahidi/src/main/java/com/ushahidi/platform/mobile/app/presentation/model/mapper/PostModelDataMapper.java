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

package com.ushahidi.platform.mobile.app.presentation.model.mapper;

import com.ushahidi.platform.mobile.app.domain.entity.Post;
import com.ushahidi.platform.mobile.app.presentation.model.PostModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Maps {@link Post} onto {@link PostModel}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostModelDataMapper {

    private TagModelDataMapper mTagModelDataMapper;

    private PostValueModelDataMapper mPostValueModelDataMapper;

    private PostFormModelDataMapper mPostFormModelDataMapper;

    private PostCompletedStagesDataModelDataMapper mPostCompletedStagesDataModelDataMapper;

    private AllowedPrivilegesModelDataMapper mAllowedPrivilegesModelDataMapper;

    private PostUserModelDataMapper mPostUserModelDataMapper;

    private PostPublishedToModelDataMapper mPostPublishedToModelDataMapper;

    /**
     * Default constructor
     *
     * @param tagModelDataMapper       The tag model data mapper to use for initialization
     * @param postValueModelDataMapper The post value model data mapper to use for initialization
     * @param postFormModelDataMapper  The form model data mapper
     */
    @Inject
    public PostModelDataMapper(TagModelDataMapper tagModelDataMapper,
            PostValueModelDataMapper postValueModelDataMapper,
            PostFormModelDataMapper postFormModelDataMapper,
            PostCompletedStagesDataModelDataMapper postCompletedStagesDataModelDataMapper,
            AllowedPrivilegesModelDataMapper allowedPrivilegesModelDataMapper,
            PostUserModelDataMapper postUserModelDataMapper,
            PostPublishedToModelDataMapper postPublishedToModelDataMapper) {
        mTagModelDataMapper = tagModelDataMapper;
        mPostValueModelDataMapper = postValueModelDataMapper;
        mPostFormModelDataMapper = postFormModelDataMapper;
        mPostCompletedStagesDataModelDataMapper = postCompletedStagesDataModelDataMapper;
        mAllowedPrivilegesModelDataMapper = allowedPrivilegesModelDataMapper;
        mPostUserModelDataMapper = postUserModelDataMapper;
        mPostPublishedToModelDataMapper = postPublishedToModelDataMapper;
    }

    /**
     * Maps {@link Post} to {@link Post}
     *
     * @param post The {@link Post} to be
     *             mapped
     * @return The {@link Post} entity
     */
    public PostModel map(@NonNull Post post) {
        PostModel postModel = new PostModel();
        postModel._id = post._id;
        postModel.setStatus(PostModel.Status.valueOf(post.getStatus().name()));
        postModel.setTitle(post.getTitle());
        postModel.setCreated(post.getCreated());
        postModel.setUpdated(post.getUpdated());
        postModel.setType(PostModel.Type.valueOf(post.getType().name()));
        postModel.setSlug(post.getSlug());
        postModel.setTags(mTagModelDataMapper.map(post.getTags()));
        postModel.setAuthorEmail(post.getAuthorEmail());
        postModel.setAuthorRealname(post.getAuthorRealname());
        postModel.setContent(post.getContent());
        postModel.setDeploymentId(post.getDeploymentId());
        postModel.setParent(post.getParent());
        postModel.setValues(mPostValueModelDataMapper.map(post.getValues()));
        postModel.setCompletedStages(post.getCompletedStages());
        postModel.setFormModel(mPostFormModelDataMapper.map(post.getPostForm()));
        postModel.setPostUserModel(mPostUserModelDataMapper.map(post.getPostUser()));

        return postModel;
    }

    /**
     * Maps {@link PostModel} onto {@link Post}
     *
     * @param postModel The post model to be mapped
     * @return The Post
     */
    public Post map(@NonNull PostModel postModel) {
        Post post = new Post();
        post._id = postModel._id;
        post.setStatus(Post.Status.valueOf(postModel.getStatus().name()));
        post.setTitle(postModel.getTitle());
        post.setCreated(postModel.getCreated());
        post.setUpdated(postModel.getUpdated());
        post.setType(Post.Type.valueOf(postModel.getType().name()));
        post.setSlug(postModel.getSlug());
        post.setTags(mTagModelDataMapper.unmap(postModel.getTags()));
        post.setAuthorEmail(postModel.getAuthorEmail());
        post.setAuthorRealname(postModel.getAuthorRealname());
        post.setContent(postModel.getContent());
        post.setDeploymentId(postModel.getDeploymentId());
        post.setValues(mPostValueModelDataMapper.map(postModel.getValues()));
        post.setCompletedStages(postModel.getCompletedStages());
        post.setPostForm(mPostFormModelDataMapper.map(postModel.getPostFormModel()));
        return post;
    }

    /**
     * Maps a list {@link Post} into a list of {@link Post}.
     *
     * @param postList List to be mapped.
     * @return {@link Post}
     */
    public List<PostModel> map(List<Post> postList) {
        List<PostModel> postModelList = new ArrayList<>();
        PostModel postModel;
        for (Post post : postList) {
            postModel = map(post);
            if (postModel != null) {
                postModelList.add(postModel);
            }
        }
        return postModelList;
    }
}
