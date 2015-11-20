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

package com.ushahidi.platform.mobile.app.presentation.presenter.account;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.platform.mobile.app.data.PrefsFactory;
import com.ushahidi.platform.mobile.app.data.api.account.PlatformSession;
import com.ushahidi.platform.mobile.app.data.api.account.SessionManager;
import com.ushahidi.platform.mobile.app.domain.entity.Deployment;
import com.ushahidi.platform.mobile.app.domain.entity.UserAccount;
import com.ushahidi.platform.mobile.app.domain.entity.UserProfile;
import com.ushahidi.platform.mobile.app.domain.usecase.account.LoginUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.user.FetchUserProfileUsecase;
import com.ushahidi.platform.mobile.app.presentation.exception.ErrorMessageFactory;
import com.ushahidi.platform.mobile.app.presentation.model.UserAccountModel;
import com.ushahidi.platform.mobile.app.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.platform.mobile.app.presentation.model.mapper.UserAccountModelDataMapper;
import com.ushahidi.platform.mobile.app.presentation.view.account.LoginView;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between {@link LoginView} and
 * {@link com.ushahidi.platform.mobile.app.presentation.model.DeploymentModel} of the presentation layer.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LoginPresenter implements Presenter {

    private final LoginUsecase mLoginUsecase;

    private final Usecase mListDeploymentUsecase;

    private final FetchUserProfileUsecase mGetUserProfileUsecase;

    private final UserAccountModelDataMapper mUserAccountModelDataMapper;

    private final DeploymentModelDataMapper mDeploymentModelDataMapper;

    private final SessionManager<PlatformSession> mSessionManager;

    private LoginView mLoginView;

    private PrefsFactory mPrefsFactory;

    /**
     * Default presenter
     *
     * @param loginUsecase               The login use case
     * @param listDeploymentUsecase      The list deployment use case
     * @param userProfileUsecase         The user profile use case
     * @param sessionManager             The session manager
     * @param prefsFactory               The preference factory
     * @param userAccountModelDataMapper The user account model data mapper
     * @param deploymentModelDataMapper  The deployment model data mapper
     */
    @Inject
    public LoginPresenter(@Named("accountLogin") LoginUsecase loginUsecase,
            @Named("deploymentList") Usecase listDeploymentUsecase,
            @Named("userprofileFetch") FetchUserProfileUsecase userProfileUsecase,
            SessionManager<PlatformSession> sessionManager,
            PrefsFactory prefsFactory,
            UserAccountModelDataMapper userAccountModelDataMapper,
            DeploymentModelDataMapper deploymentModelDataMapper) {
        mLoginUsecase = loginUsecase;
        mGetUserProfileUsecase = userProfileUsecase;
        mListDeploymentUsecase = listDeploymentUsecase;
        mSessionManager = sessionManager;
        mPrefsFactory = prefsFactory;
        mUserAccountModelDataMapper = userAccountModelDataMapper;
        mDeploymentModelDataMapper = deploymentModelDataMapper;
    }

    @Override
    public void resume() {
        // Do nothing
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public void destroy() {
        mLoginUsecase.unsubscribe();
        mListDeploymentUsecase.unsubscribe();
        mGetUserProfileUsecase.unsubscribe();
    }

    public void setView(@NonNull LoginView loginView) {
        mLoginView = loginView;
    }

    /**
     * Performs a user login, upon successful login, it fetches users profile via the API.
     *
     * @param userAccountModel The user to be logged in
     */
    public void performLogin(UserAccountModel userAccountModel) {
        mLoginView.showLoading();
        UserAccount userAccount = mUserAccountModelDataMapper.map(userAccountModel);
        // Use the user account to hold the deployment ID. So it can be passed along to the
        // Auth token
        userAccount._id = mPrefsFactory.getActiveDeploymentId().get();
        mLoginUsecase.setUserAccount(userAccount);
        mLoginUsecase.execute(new DefaultSubscriber<Boolean>() {
            @Override
            public void onCompleted() {
                mLoginView.hideLoading();
            }

            @Override
            public void onNext(Boolean status) {
                getUserProfile();
            }

            @Override
            public void onError(Throwable e) {
                mLoginView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mLoginView.showRetry();
            }
        });
    }

    /**
     * Gets user deployment list by executing the list deployment use case
     */
    public void getDeploymentList() {
        mListDeploymentUsecase.execute(new DefaultSubscriber<List<Deployment>>() {
            @Override
            public void onCompleted() {
                mLoginView.hideLoading();
            }

            @Override
            public void onNext(List<Deployment> deploymentList) {
                mLoginView.deploymentList(mDeploymentModelDataMapper.map(deploymentList));
            }

            @Override
            public void onError(Throwable e) {
                mLoginView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
            }
        });
    }

    /**
     * Get user profile
     */
    public void getUserProfile() {
        mGetUserProfileUsecase.setDeploymentId(mPrefsFactory.getActiveDeploymentId().get());
        mGetUserProfileUsecase.execute(new DefaultSubscriber<UserProfile>() {
            @Override
            public void onStart() {
                mLoginView.showLoading();
            }

            @Override
            public void onCompleted() {
                mLoginView.hideLoading();
                mLoginView.loginCompleted();
            }

            @Override
            public void onNext(UserProfile userProfile) {
                mSessionManager.setActiveSession(
                        new PlatformSession(userProfile._id, userProfile.getUsername(),
                                userProfile.getDeploymentId()));
                mLoginView.loginCompleted();
            }

            @Override
            public void onError(Throwable e) {
                mLoginView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory
                .create(mLoginView.getAppContext(), errorHandler.getException());
        mLoginView.showError(errorMessage);
    }
}
