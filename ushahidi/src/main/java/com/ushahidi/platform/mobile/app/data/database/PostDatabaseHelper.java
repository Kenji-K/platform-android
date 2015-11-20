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

package com.ushahidi.platform.mobile.app.data.database;

import com.ushahidi.platform.mobile.app.data.entity.FormAttributeEntity;
import com.ushahidi.platform.mobile.app.data.entity.FormEntity;
import com.ushahidi.platform.mobile.app.data.entity.GeoJsonEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostFormEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostTagEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostUserEntity;
import com.ushahidi.platform.mobile.app.data.entity.TagEntity;
import com.ushahidi.platform.mobile.app.data.exception.AddPostException;
import com.ushahidi.platform.mobile.app.data.exception.PostNotFoundException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Posts database helper
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostDatabaseHelper extends BaseDatabaseHelper {

    private AddPostException mException;

    /**
     * Default constructor
     *
     * @param context The calling context. Cannot be a null value
     */
    @Inject
    public PostDatabaseHelper(@NonNull Context context) {
        super(context);
    }

    /**
     * Get a list of {@link PostEntity}
     *
     * @param deploymentId The deployment the post entity belongs to
     * @return An Observable that emits a list of {@link PostEntity}
     */
    public Observable<List<PostEntity>> getPostList(Long deploymentId) {
        return Observable.create(subscriber -> {
            final List<PostEntity> postEntities = getPosts(deploymentId);
            if (postEntities != null) {
                subscriber.onNext(setPostEntityList(postEntities));
                subscriber.onCompleted();
            } else {
                subscriber.onError(new PostNotFoundException());
            }
        });
    }

    /**
     * Get a single {@link PostEntity}
     *
     * @param deploymentId The deployment Id associated with the post entity
     * @param postId       The post id to be used to retrieve the {@link PostEntity}
     * @return An observable that emits a {@link PostEntity}
     */
    public Observable<PostEntity> getPostEntity(Long deploymentId, Long postId) {
        return Observable.create(subscriber -> {
            final PostEntity postEntity = get(postId, deploymentId);
            if (postEntity != null) {
                List<TagEntity> tags = getTagEntity(postEntity);
                postEntity.setPostUser(getPostUserEntity(postEntity));
                postEntity.setPostForm(getPostFormEntity(postEntity));
                postEntity.setTags(tags);
                subscriber.onNext(postEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new PostNotFoundException());
            }
        });

    }

    /**
     * Saves a list of {@link PostEntity}
     *
     * @param postEntities The post entities
     * @return An observable that emits a {@link PostEntity}
     */
    public Observable<Long> putPosts(List<PostEntity> postEntities) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                for (PostEntity postEntity : postEntities) {
                    // Delete existing posttag entities.
                    // Lame way to avoid duplicates because the ID is auto generated upon insertion
                    // and we wouldn't know by then to replace them.
                    deletePostTagEntity(postEntity.getDeploymentId(), postEntity._id);
                    puts(postEntity, subscriber);
                }
            }
        });
    }

    /**
     * Saves a list of {@link PostEntity}
     *
     * @param postEntity The post entity
     * @return An observable that emits a {@link PostEntity}
     */
    public Observable<Long> putPost(PostEntity postEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                // Delete existing posttag entities.
                // Lame way to avoid duplicates because the ID is auto generated upon insertion
                // and we wouldn't know by then to replace them.
                deletePostTagEntity(postEntity.getDeploymentId(), postEntity._id);
                puts(postEntity, subscriber);
            }
        });
    }

    /**
     * Saves a list of {@PostEntity}
     *
     * @param deploymentId  The deployment ID
     * @param tagEntities   The Tag entities
     * @param postEntities  The post entities
     * @param geoJsonEntity The GeoJson entity
     * @param formEntities  The           form entities
     * @return An observable that emits {@link PostEntity}
     */
    public List<PostEntity> putFetchedPosts(Long deploymentId,
            List<TagEntity> tagEntities,
            List<PostEntity> postEntities,
            GeoJsonEntity geoJsonEntity,
            List<FormEntity> formEntities) {
        // Note: Saving other entity types apart from post because it was easier to save
        // all the different entity types fetched via the API request.
        if (!isClosed()) {
            cupboard().withDatabase(getWritableDatabase()).put(tagEntities);
            cupboard().withDatabase(getWritableDatabase()).put(geoJsonEntity);
            cupboard().withDatabase(getWritableDatabase()).put(formEntities);
            for (PostEntity postEntity : postEntities) {
                // Delete existing posttag entities.
                // Lame way to avoid duplicates because the ID is auto generated upon insertion
                // and we wouldn't know by then to replace them.
                deletePostTagEntity(postEntity.getDeploymentId(), postEntity._id);
                deletePostUserEntity(postEntity.getDeploymentId(), postEntity._id);
                deletePostFormEntity(postEntity.getDeploymentId(), postEntity._id);
                puts(postEntity);
            }
        }
        List<PostEntity> postEntityList = getPosts(deploymentId);
        return setPostEntityList(postEntityList);
    }

    public void putFetchedFormAttributes(List<FormAttributeEntity> formAttributes) {
        if (!isClosed()) {
            cupboard().withDatabase(getWritableDatabase()).put(formAttributes);
        }
    }

    /**
     * Deletes a post entity
     *
     * @param postEntity The post entity
     * @return True upon successful deletion, otherwise false
     */
    public Observable<Boolean> deletePost(PostEntity postEntity) {
        return Observable.create(subscriber -> {
            if (!isClosed()) {
                Boolean status = false;
                try {
                    status = cupboard().withDatabase(getWritableDatabase()).delete(postEntity);
                    if (status) {
                        // Delete tags associated with the post to keep them from being
                        // orphaned
                        deletePostTagEntity(postEntity.getDeploymentId(), postEntity._id);
                        deletePostUserEntity(postEntity.getDeploymentId(), postEntity._id);
                        deletePostFormEntity(postEntity.getDeploymentId(), postEntity._id);
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(status);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * Basic search for deployment
     *
     * @param deploymentId The deployment id
     * @param query        The search query. Should be either the post title or description
     * @return An observable that emits list of {@link PostEntity}
     */
    public Observable<List<PostEntity>> search(Long deploymentId, String query) {
        return Observable.create(subscriber -> {
            String selection = " mTitle LIKE ? OR mContent LIKE ? AND mDeploymentId = ?";
            String args[] = {query + "%", query + "%", String.valueOf(deploymentId)};
            // Post title holds the search term
            List<PostEntity> postEntities = cupboard().withDatabase(getReadableDatabase()).query(
                    PostEntity.class).withSelection(selection, args).list();
            subscriber.onNext(setPostEntityList(postEntities));
            subscriber.onCompleted();
        });
    }

    /**
     * Basic search for deployment
     *
     * @param deploymentId The deployment id
     * @param query        The search query. Should be either the post title or description
     * @return A list {@link PostEntity}
     */
    public List<PostEntity> searchQuery(Long deploymentId, String query) {
        String selection = " mTitle LIKE ? OR mContent LIKE ? AND mDeploymentId = ?";
        String args[] = {query + "%", query + "%", String.valueOf(deploymentId)};
        // Post title holds the search term
        List<PostEntity> postEntities = cupboard().withDatabase(getReadableDatabase())
                .query(PostEntity.class).withSelection(selection, args).list();
        return setPostEntityList(postEntities);
    }

    private List<PostEntity> getPosts(final Long deploymentId) {
        return cupboard().withDatabase(getReadableDatabase()).query(PostEntity.class)
                .withSelection("mDeploymentId = ?", String.valueOf(deploymentId)).list();
    }

    private PostEntity get(Long postId, Long deploymentId) {
        return cupboard().withDatabase(getReadableDatabase()).query(PostEntity.class)
                .withSelection("mDeploymentId = ? ", String.valueOf(deploymentId)).byId(postId)
                .get();
    }

    private List<TagEntity> getTagEntity(PostEntity postEntity) {
        List<TagEntity> tagEntityList = new ArrayList<>();
        String selection = "mDeploymentId = ? AND mPostId = ?";
        String args[] = {String.valueOf(postEntity.getDeploymentId()),
                String.valueOf(postEntity._id)};
        // Fetch Tags attached to a post by querying the post entity
        // table to get the tag IDs
        List<PostTagEntity> postTagEntityList = cupboard().withDatabase(getReadableDatabase())
                .query(PostTagEntity.class).withSelection(selection, args).list();
        // Iterate through the fetched post tag entity to fetch for the
        // tags attached to the post. This is a manual way of dealing
        // entity relationships
        for (PostTagEntity postTagEntity : postTagEntityList) {
            String sel = "mDeploymentId = ? AND _id = ?";
            String arg[] = {String.valueOf(postEntity.getDeploymentId()),
                    String.valueOf(postTagEntity.getTagId())};
            TagEntity tagEntity = cupboard().withDatabase(getReadableDatabase())
                    .query(TagEntity.class).withSelection(sel, arg).get();
            tagEntityList.add(tagEntity);
        }
        return tagEntityList;
    }

    private PostUserEntity getPostUserEntity(PostEntity postEntity) {
        String selection = "mDeploymentId = ? AND mPostId = ?";
        String args[] = {String.valueOf(postEntity.getDeploymentId()),
                String.valueOf(postEntity._id)};

        return cupboard().withDatabase(getReadableDatabase()).query(PostUserEntity.class)
                .withSelection(selection, args).get();
    }

    private PostFormEntity getPostFormEntity(PostEntity postEntity) {
        String selection = "mDeploymentId = ? AND mPostId = ?";
        String args[] = {String.valueOf(postEntity.getDeploymentId()),
                String.valueOf(postEntity._id)};

        return cupboard().withDatabase(getReadableDatabase()).query(PostFormEntity.class)
                .withSelection(selection, args).get();
    }

    private void puts(final PostEntity postEntity, Subscriber subscribers) {
        Long rows = puts(postEntity);
        if (rows != null) {
            subscribers.onNext(rows);
            subscribers.onCompleted();
        } else {
            subscribers.onError(mException);
        }
    }

    private Long puts(final PostEntity postEntity) {
        SQLiteDatabase db = getReadableDatabase();
        Long rows = null;
        try {
            db.beginTransaction();
            rows = cupboard().withDatabase(db).put(postEntity);
            if ((rows > 0) && (postEntity.getPostTagEntityList() != null) && (
                    postEntity.getPostTagEntityList().size() > 0)) {
                // Put post tag entity
                for (PostTagEntity postTagEntity : postEntity.getPostTagEntityList()) {
                    postTagEntity.setPostId(postEntity._id);
                    postTagEntity.setDeploymentId(postEntity.getDeploymentId());
                    cupboard().withDatabase(db).put(postTagEntity);
                }
                // Put post user entity
                PostUserEntity postUser = postEntity.getPostUser();
                if (postUser != null) {
                    postUser.setDeploymentId(postEntity.getDeploymentId());
                    postUser.setPostId(rows);
                    cupboard().withDatabase(db).put(postUser);
                }

                // Put post form entity
                PostFormEntity postFormEntity = postEntity.getPostFormEntity();
                if (postFormEntity != null) {
                    postFormEntity.setDeploymentId(postEntity.getDeploymentId());
                    postFormEntity.setPostId(rows);
                    cupboard().withDatabase(db).put(postFormEntity);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            mException = new AddPostException(e);
        } finally {
            if (db != null) {
                db.endTransaction();
            }
        }
        return rows;
    }

    private void deletePostTagEntity(Long deploymentId, Long postId) {
        final String[] selectionArgs = {String.valueOf(deploymentId), String.valueOf(postId)};
        final String selection = "mDeploymentId = ? AND mPostId = ?";
        cupboard().withDatabase(getWritableDatabase())
                .delete(PostTagEntity.class, selection, selectionArgs);
    }

    private void deletePostUserEntity(Long deploymentId, Long postId) {
        final String[] selectionArgs = {String.valueOf(deploymentId), String.valueOf(postId)};
        final String selection = "mDeploymentId = ? AND mPostId = ?";
        cupboard().withDatabase(getWritableDatabase())
                .delete(PostUserEntity.class, selection, selectionArgs);
    }

    private void deletePostFormEntity(Long deploymentId, Long postId) {
        final String[] selectionArgs = {String.valueOf(deploymentId), String.valueOf(postId)};
        final String selection = "mDeploymentId = ? AND mPostId = ?";
        cupboard().withDatabase(getWritableDatabase())
                .delete(PostFormEntity.class, selection, selectionArgs);
    }

    private List<PostEntity> setPostEntityList(List<PostEntity> postEntities) {
        final List<PostEntity> postEntityList = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            List<TagEntity> tags = getTagEntity(postEntity);
            postEntity.setPostUser(getPostUserEntity(postEntity));
            postEntity.setPostForm(getPostFormEntity(postEntity));
            postEntity.setTags(tags);
            postEntityList.add(postEntity);
        }
        return postEntityList;
    }

    /**
     * Clears all entries in the table
     */
    public void clearEntries() {
        cupboard().withDatabase(getWritableDatabase()).delete(PostEntity.class, null);
    }
}
