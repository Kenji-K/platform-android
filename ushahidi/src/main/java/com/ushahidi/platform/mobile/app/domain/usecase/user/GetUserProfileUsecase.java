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

package com.ushahidi.platform.mobile.app.domain.usecase.user;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.platform.mobile.app.domain.repository.UserProfileRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Usecase for listing {@link com.ushahidi.platform.mobile.app.domain.entity.UserProfile} either from online
 * or the local store.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class GetUserProfileUsecase extends Usecase {

    private final UserProfileRepository mUserProfileRepository;

    private Long mDeploymentId = null;

    private Long mUserProfileId = null;

    /**
     * Default constructor
     *
     * @param userProfileRepository The user profile repository
     * @param threadExecutor        The thread executor
     * @param postExecutionThread   The post execution thread
     */
    @Inject
    protected GetUserProfileUsecase(UserProfileRepository userProfileRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserProfileRepository = userProfileRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.platform.mobile.app.domain.entity.Tag}
     * and where to fetch it from.
     *
     * @param deploymentId  The deploymentId associated with the GeoJson
     * @param userProfileId The profile ID
     */
    public void setListUserProfile(Long deploymentId, Long userProfileId) {
        mDeploymentId = deploymentId;
        mUserProfileId = userProfileId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mUserProfileId == null) {
            throw new RuntimeException(
                    "Deployment id and from cannot be null. You must call setListUserProfile(...)");
        }
        return mUserProfileRepository.getUserProfile(mDeploymentId, mUserProfileId);
    }
}
