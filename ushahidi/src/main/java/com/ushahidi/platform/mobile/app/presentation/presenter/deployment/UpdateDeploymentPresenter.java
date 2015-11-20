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

package com.ushahidi.platform.mobile.app.presentation.presenter.deployment;

import android.support.annotation.NonNull;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.platform.mobile.app.data.entity.DeploymentEntity;
import com.ushahidi.platform.mobile.app.domain.usecase.deployment.FetchDeploymentUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.deployment.UpdateDeploymentUsecase;
import com.ushahidi.platform.mobile.app.presentation.exception.ErrorMessageFactory;
import com.ushahidi.platform.mobile.app.presentation.model.DeploymentModel;
import com.ushahidi.platform.mobile.app.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.platform.mobile.app.presentation.view.deployment.UpdateDeploymentView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class UpdateDeploymentPresenter implements Presenter {

    private final UpdateDeploymentUsecase mUpdateDeploymentUsecase;

    private final FetchDeploymentUsecase mFetchDeploymentUsecase;

    private final DeploymentModelDataMapper mDeploymentModelDataMapper;

    private UpdateDeploymentView mUpdateDeploymentView;

    /**
     * Default use case.
     *
     * @param updateDeploymentUsecase   The update deployment use case
     * @param deploymentModelDataMapper The deployment model data mapper
     */
    @Inject
    public UpdateDeploymentPresenter(
            @Named("categoryUpdate") UpdateDeploymentUsecase updateDeploymentUsecase,
            FetchDeploymentUsecase fetchDeploymentUsecase,
            DeploymentModelDataMapper deploymentModelDataMapper) {
        mUpdateDeploymentUsecase = updateDeploymentUsecase;
        mDeploymentModelDataMapper = deploymentModelDataMapper;
        mFetchDeploymentUsecase = fetchDeploymentUsecase;
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
        mUpdateDeploymentUsecase.unsubscribe();
    }

    public void setView(@NonNull UpdateDeploymentView addDeploymentView) {
        mUpdateDeploymentView = addDeploymentView;
    }

    /**
     * Updates {@link DeploymentModel}
     *
     * @param deploymentModel The deployment model to be updated
     */
    public void updateDeployment(DeploymentModel deploymentModel) {
        mUpdateDeploymentView.hideRetry();
        mUpdateDeploymentView.showLoading();
        mUpdateDeploymentUsecase.setDeployment(mDeploymentModelDataMapper.map(deploymentModel));
        mUpdateDeploymentUsecase.execute(new DefaultSubscriber<Long>() {
            @Override
            public void onCompleted() {
                mUpdateDeploymentView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mUpdateDeploymentView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mUpdateDeploymentView.showRetry();
            }

            @Override
            public void onNext(Long row) {
                mUpdateDeploymentView.onDeploymentSuccessfullyUpdated(row);
            }
        });
    }

    public void submitUrl(String url) {
        mUpdateDeploymentView.showLoading();
        mFetchDeploymentUsecase.setDeploymentUrl(url);
        mFetchDeploymentUsecase.execute(new DefaultSubscriber<DeploymentEntity>() {
            @Override
            public void onCompleted() {
                mUpdateDeploymentView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mUpdateDeploymentView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mUpdateDeploymentView.showRetry();
            }

            @Override
            public void onNext(DeploymentEntity deploymentEntity) {
                DeploymentModel deploymentModel = new DeploymentModel();
                deploymentModel.setTitle(deploymentEntity.getTitle());
                deploymentModel.setUrl(url);
                updateDeployment(deploymentModel);
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mUpdateDeploymentView.getAppContext(),
                errorHandler.getException());
        mUpdateDeploymentView.showError(errorMessage);
    }
}