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

package com.ushahidi.platform.mobile.app.presentation.util;

import com.ushahidi.platform.mobile.app.domain.entity.PostPublishedTo;
import com.ushahidi.platform.mobile.app.presentation.model.PostFormModel;
import com.ushahidi.platform.mobile.app.presentation.model.PostModel;
import com.ushahidi.platform.mobile.app.presentation.model.PostTagModel;
import com.ushahidi.platform.mobile.app.presentation.model.PostUserModel;
import com.ushahidi.platform.mobile.app.presentation.model.PostValueModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * With the many properties for the {@link PostModel} constructing them by hand is messy
 * and prone to error so providing flexible way to construct the PostModel for submission
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class ConfigurePostModelUtility {

    private PostModel mPostModel;

    private ConfigurePostModelUtility(PostModel postModel) {
        mPostModel = postModel;
    }

    public PostModel getPostModel() {
        return mPostModel;
    }

    public static class Builder {

        private Long mDeploymentId;

        private PostModel mPostModel;

        public Builder(@NonNull Long deploymentId, @NonNull String title,
                @NonNull String content) {
            mDeploymentId = deploymentId;
            mPostModel = new PostModel();
            mPostModel.setTitle(title);
            mPostModel.setContent(content);
            mPostModel.setDeploymentId(deploymentId);
            mPostModel.setStatus(PostModel.Status.DRAFT);
        }

        public Builder postUserId(Long userId) {
            PostUserModel postUserModel = new PostUserModel();
            postUserModel.setDeploymentId(mDeploymentId);
            postUserModel.setUserId(userId);
            mPostModel.setPostUserModel(postUserModel);
            return this;
        }

        public Builder postFormId(Long formId) {
            PostFormModel postFormModel = new PostFormModel();
            postFormModel.setDeploymentId(mDeploymentId);
            postFormModel.setFormId(formId);
            mPostModel.setFormModel(postFormModel);
            return this;
        }

        public Builder postPublishTo(String to) {
            PostPublishedTo postPublishedTo = new PostPublishedTo();
            postPublishedTo.setPublishedTo(to);
            mPostModel.setPostPublishedTo(postPublishedTo);
            return this;
        }

        public Builder postValue(PostValueUtility postValue) {
            PostValueModel postValueModel = new PostValueModel();
            postValueModel.setDeploymentId(mDeploymentId);
            postValueModel.setValues(postValue.getValue());
            mPostModel.setValues(postValueModel);
            return this;
        }

        public Builder postTags(List<Long> tagIds) {
            List<PostTagModel> postTagModels = new ArrayList<>();
            for (Long tagId : tagIds) {
                PostTagModel postTagModel = new PostTagModel();
                postTagModel.setTagId(tagId);
            }
            mPostModel.setPostTagEntityList(postTagModels);
            return this;
        }

        public ConfigurePostModelUtility build() {
            return new ConfigurePostModelUtility(mPostModel);
        }
    }
}
