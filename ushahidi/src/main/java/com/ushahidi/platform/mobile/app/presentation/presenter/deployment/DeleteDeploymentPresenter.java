package com.ushahidi.platform.mobile.app.presentation.presenter.deployment;

import android.support.annotation.NonNull;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.platform.mobile.app.domain.usecase.form.DeleteFormUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.geojson.DeleteGeoJsonUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.post.DeletePostUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.tag.DeleteTagUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.user.DeleteUserProfileUsecase;
import com.ushahidi.platform.mobile.app.domain.usecase.deployment.DeleteDeploymentUsecase;
import com.ushahidi.platform.mobile.app.presentation.exception.ErrorMessageFactory;
import com.ushahidi.platform.mobile.app.presentation.view.deployment.DeleteDeploymentView;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscriber;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteDeploymentPresenter implements Presenter {

    private final DeleteDeploymentUsecase mDeleteDeploymentUsecase;

    private final DeleteFormUsecase mDeleteFormUsecase;

    private final DeleteGeoJsonUsecase mDeleteGeoJsonUsecase;

    private final DeletePostUsecase mDeletePostUsecase;

    private final DeleteTagUsecase mDeleteTagUsecase;

    private final DeleteUserProfileUsecase mDeleteUserProfileUsecase;

    private DeleteDeploymentView mDeleteDeploymentView;

    private Subscriber deleteDeploymentUseCaseSubscriber = new DefaultSubscriber<Long>() {
        @Override
        public void onCompleted() {
            mDeleteFormUsecase.execute(deleteFormUseCaseSubscriber);
            // TODO: Delete all saved items in the shared preferences
        }

        @Override
        public void onNext(Long row) {
            mDeleteDeploymentView.onDeploymentDeleted();
        }

        @Override
        public void onError(Throwable e) {
            mDeleteDeploymentView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    };

    private Subscriber deleteFormUseCaseSubscriber = new DefaultSubscriber<Boolean>() {
        @Override
        public void onCompleted() {
            mDeleteGeoJsonUsecase.execute(deleteGeoJsonUseCaseSubscriber);
        }

        @Override
        public void onNext(Boolean row) {
            // Do nothing;
        }

        @Override
        public void onError(Throwable e) {
            mDeleteDeploymentView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    };

    private Subscriber deleteGeoJsonUseCaseSubscriber = new DefaultSubscriber<Boolean>() {
        @Override
        public void onCompleted() {
            mDeletePostUsecase.execute(deletePostUseCaseSubscriber);
        }

        @Override
        public void onNext(Boolean row) {
            // Do nothing;
        }

        @Override
        public void onError(Throwable e) {
            mDeleteDeploymentView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    };

    private Subscriber deletePostUseCaseSubscriber = new DefaultSubscriber<Boolean>() {
        @Override
        public void onCompleted() {
            mDeleteTagUsecase.execute(deleteTagUseCaseSubscriber);
        }

        @Override
        public void onNext(Boolean row) {
            // Do nothing;
        }

        @Override
        public void onError(Throwable e) {
            mDeleteDeploymentView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    };

    private Subscriber deleteTagUseCaseSubscriber = new DefaultSubscriber<Boolean>() {
        @Override
        public void onCompleted() {
            mDeleteUserProfileUsecase.execute(deleteUserProfileUseCaseSubscriber);
        }

        @Override
        public void onNext(Boolean row) {
            // Do nothing;
        }

        @Override
        public void onError(Throwable e) {
            mDeleteDeploymentView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    };

    private Subscriber deleteUserProfileUseCaseSubscriber = new DefaultSubscriber<Boolean>() {
        @Override
        public void onCompleted() {
            mDeleteDeploymentView.hideLoading();
        }

        @Override
        public void onNext(Boolean row) {
            // Do nothing;
        }

        @Override
        public void onError(Throwable e) {
            mDeleteDeploymentView.hideLoading();
            showErrorMessage(new DefaultErrorHandler((Exception) e));
        }
    };

    /**
     * Default constructor
     *
     * @param deleteDeploymentUsecase The delete deployment use case
     */
    @Inject
    public DeleteDeploymentPresenter(@Named("categoryDelete") DeleteDeploymentUsecase deleteDeploymentUsecase,
            @Named("categoryDelete") DeleteFormUsecase deleteFormUsecase,
            @Named("categoryDelete") DeleteGeoJsonUsecase deleteGeoJsonUsecase,
            @Named("categoryDelete") DeletePostUsecase deletePostUsecase,
            @Named("categoryDelete") DeleteTagUsecase deleteTagUsecase,
            @Named("categoryDelete") DeleteUserProfileUsecase deleteUserProfileUsecase) {
        mDeleteDeploymentUsecase = deleteDeploymentUsecase;
        mDeleteFormUsecase = deleteFormUsecase;
        mDeleteGeoJsonUsecase = deleteGeoJsonUsecase;
        mDeletePostUsecase = deletePostUsecase;
        mDeleteTagUsecase = deleteTagUsecase;
        mDeleteUserProfileUsecase = deleteUserProfileUsecase;
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
        mDeleteDeploymentUsecase.unsubscribe();
    }

    public void setView(@NonNull DeleteDeploymentView deleteDeploymentView) {
        mDeleteDeploymentView = deleteDeploymentView;
    }

    /**
     * Deletes a deployment model from storage
     *
     * @param deploymentId The deployment ID to be used for deletion
     */
    public void deleteDeployment(Long deploymentId) {
        mDeleteDeploymentUsecase.setDeploymentId(deploymentId);
        mDeleteFormUsecase.setDeploymentId(deploymentId);
        mDeleteGeoJsonUsecase.setDeploymentId(deploymentId);
        mDeletePostUsecase.setDeploymentId(deploymentId);
        mDeleteTagUsecase.setDeploymentId(deploymentId);
        mDeleteUserProfileUsecase.setDeploymentId(deploymentId);
        mDeleteDeploymentUsecase.execute(deleteDeploymentUseCaseSubscriber);
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory.create(mDeleteDeploymentView.getAppContext(),
                errorHandler.getException());
        mDeleteDeploymentView.showError(errorMessage);
    }
}
