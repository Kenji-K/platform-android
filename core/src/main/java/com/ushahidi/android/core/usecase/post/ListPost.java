/*
 * Copyright (c) 2014 Ushahidi.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.core.usecase.post;

import com.ushahidi.android.core.entity.Post;
import com.ushahidi.android.core.exception.ErrorWrap;
import com.ushahidi.android.core.repository.IPostRepository;
import com.ushahidi.android.core.task.PostExecutionThread;
import com.ushahidi.android.core.task.ThreadExecutor;

import java.util.List;

/**
 * Implements {@link IListPost} that fetches posts
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListPost implements IListPost {

    private IPostRepository mIPostRepository;

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private Callback mCallback;

    //TODO initialize this in the execute method
    private long mDeploymentId;

    private final IPostRepository.PostListCallback mRepositoryCallback =
        new IPostRepository.PostListCallback() {

            @Override
            public void onPostListLoaded(List<Post> postList) {
                notifySuccess(postList);
            }

            @Override
            public void onError(ErrorWrap errorWrap) {
                notifyFailure(errorWrap);
            }
        };

    /**
     * Default constructor.
     *
     * @param threadExecutor      {@link ThreadExecutor} used to execute this use case in a
     *                            background thread.
     * @param postExecutionThread {@link PostExecutionThread} used to post updates when the use case
     *                            has been executed.
     */
    public ListPost(ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {

        if (threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }

        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    public void setPostRepository(IPostRepository postRepository) {

        if (postRepository == null) {
            throw new IllegalArgumentException("IPostRepository cannot be null");
        }
        mIPostRepository = postRepository;
    }

    @Override
    public void execute(final long deploymentId, Callback callback) {
        if (deploymentId < 0 || callback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        mCallback = callback;
        mDeploymentId = deploymentId;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        if (mIPostRepository == null) {
            throw new NullPointerException("You must call setPostRepository(...)");
        }
        mIPostRepository.getPostList(mDeploymentId, mRepositoryCallback);
    }

    private void notifySuccess(final List<Post> postList) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onPostListLoaded(postList);
            }
        });
    }

    private void notifyFailure(final ErrorWrap errorWrap) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(errorWrap);
            }
        });
    }
}
