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
 * Usecase for fetching user profiles from the internet
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FetchUserProfileUsecase extends Usecase {

    private final UserProfileRepository mUserProfileRepository;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param userProfileRepository The user profile repository
     * @param threadExecutor        The thread executor
     * @param postExecutionThread   The post execution thread
     */
    @Inject
    public FetchUserProfileUsecase(UserProfileRepository userProfileRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserProfileRepository = userProfileRepository;
    }

    /**
     * Sets the deployment Id
     *
     * @param deploymentId The deployment Id to be set
     */
    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null) {
            throw new RuntimeException(
                    "Deployment id and from cannot be null. You must call setDeploymentId(...)");
        }
        return mUserProfileRepository.fetchUserProfile(mDeploymentId);
    }
}
