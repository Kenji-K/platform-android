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

package com.ushahidi.platform.mobile.app.data.repository.datasource.post;

import com.google.gson.JsonElement;

import com.ushahidi.platform.mobile.app.data.api.PostApi;
import com.ushahidi.platform.mobile.app.data.api.model.FormAttributes;
import com.ushahidi.platform.mobile.app.data.api.model.FormStages;
import com.ushahidi.platform.mobile.app.data.api.model.Forms;
import com.ushahidi.platform.mobile.app.data.api.model.Posts;
import com.ushahidi.platform.mobile.app.data.api.model.Tags;
import com.ushahidi.platform.mobile.app.data.database.PostDatabaseHelper;
import com.ushahidi.platform.mobile.app.data.entity.FormAttributeEntity;
import com.ushahidi.platform.mobile.app.data.entity.FormEntity;
import com.ushahidi.platform.mobile.app.data.entity.FormStageEntity;
import com.ushahidi.platform.mobile.app.data.entity.GeoJsonEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostEntity;
import com.ushahidi.platform.mobile.app.data.entity.TagEntity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Data source for manipulating {@link com.ushahidi.platform.mobile.app.data.entity.PostEntity}
 * data
 * to and
 * from the API.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostApiDataSource implements PostDataSource {

    private PostApi mPostApi;

    private PostDatabaseHelper mPostDatabaseHelper;

    /**
     * Default constructor
     *
     * @param postApi            The post api
     * @param postDatabaseHelper The post database helper
     */
    public PostApiDataSource(@NonNull PostApi postApi,
            @NonNull PostDatabaseHelper postDatabaseHelper) {
        mPostApi = postApi;
        mPostDatabaseHelper = postDatabaseHelper;
    }

    @Override
    public Observable<Long> putPostEntities(List<PostEntity> postEntity) {
        // Do nothing. Not posting via the API ATM
        return null;
    }

    @Override
    public Observable<Long> putPostEntity(PostEntity postEntity) {
        // TODO: Implement post entity upload via the API
        return null;
    }

    @Override
    public Observable<List<PostEntity>> getPostEntityList(Long deploymentId) {
        return Observable.zip(mPostApi.getTags(), mPostApi.getPostList(), mPostApi.getGeoJson(),
                mPostApi.getForms(), mPostApi.getFormStages(), mPostApi.getFormAttributes(),
                (tags, posts, geoJsons, forms, formStages, formAttributes) -> mPostDatabaseHelper
                        .putFetchedPosts(deploymentId,
                                setTag(tags, deploymentId),
                                setPost(posts, deploymentId),
                                setGeoJson(geoJsons, deploymentId),
                                setForms(forms, deploymentId),
                                setFormStageEntity(formStages, deploymentId),
                                setFormAttributes(formAttributes, deploymentId)));
    }

    @Override
    public Observable<PostEntity> getPostEntityById(Long deploymentId, Long postEntityId) {
        // Do nothing. Not getting post by Id via the API ATM
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> deletePostEntity(PostEntity postEntity) {
        // Do nothing. Not deleting via the API ATM
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<PostEntity>> search(Long deploymentId, String query) {
        // Do nothing. Not searching via the API ATM
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> deleteDeploymentPosts(Long deploymentId) {
        // Do nothing. Not deleting via the API ATM
        throw new UnsupportedOperationException();
    }

    /**
     * Set the deployment ID for the TagModel since it's not set by the
     * API
     *
     * @param posts        The TagModel to set the deployment Id on
     * @param deploymentId The ID of the deployment to set
     * @return observable
     */
    private List<PostEntity> setPost(Posts posts, Long deploymentId) {
        List<PostEntity> postEntityList = new ArrayList<>(posts.getPosts().size());
        for (PostEntity postEntity : posts.getPosts()) {
            postEntity.setDeploymentId(deploymentId);
            postEntityList.add(postEntity);
        }
        return postEntityList;
    }

    /**
     * Set the deployment ID for the TagModel since it's not set by the
     * API
     *
     * @param tags         The TagModel to set the deployment Id on
     * @param deploymentId The ID of the deployment to set
     * @return observable
     */
    private List<TagEntity> setTag(Tags tags,
            Long deploymentId) {
        List<TagEntity> tagEntityList = new ArrayList<>(tags.getTags().size());
        for (TagEntity tagEntity : tags.getTags()) {
            tagEntity.setDeploymentId(deploymentId);
            tagEntityList.add(tagEntity);
        }
        return tagEntityList;
    }

    private GeoJsonEntity setGeoJson(JsonElement jsonElement, Long deploymentId) {
        GeoJsonEntity geoJsonEntity = new GeoJsonEntity();
        geoJsonEntity.setGeoJson(jsonElement.toString());
        geoJsonEntity.setDeploymentId(deploymentId);
        return geoJsonEntity;
    }

    private List<FormEntity> setForms(Forms forms, Long deploymentId) {
        List<FormEntity> formEntityList = new ArrayList<>();
        for (FormEntity formEntity : forms.getForms()) {
            formEntity.setDeploymentId(deploymentId);
            formEntityList.add(formEntity);
        }
        return formEntityList;
    }

    private List<FormAttributeEntity> setFormAttributes(FormAttributes formAttributes,
            Long deploymentId) {
        List<FormAttributeEntity> formAttributeEntities = new ArrayList<>();
        for (FormAttributeEntity formAttributeEntity : formAttributes.getFormAttributes()) {
            formAttributeEntity.setDeploymentId(deploymentId);
            formAttributeEntities.add(formAttributeEntity);
        }
        return formAttributeEntities;
    }

    private List<FormStageEntity> setFormStageEntity(FormStages formStages, Long deploymentId) {
        List<FormStageEntity> formStageEntities = new ArrayList<>();
        for (FormStageEntity formStageEntity : formStages.getFormStages()) {
            formStageEntity.setDeploymentId(deploymentId);
            formStageEntities.add(formStageEntity);
        }
        return formStageEntities;
    }
}
