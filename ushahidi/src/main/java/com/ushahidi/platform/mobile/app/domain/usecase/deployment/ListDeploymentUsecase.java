package com.ushahidi.platform.mobile.app.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.platform.mobile.app.domain.repository.DeploymentRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Usecase for listing {@link com.ushahidi.platform.mobile.app.domain.entity.Deployment}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    /**
     * Default constructor
     *
     * @param deploymentRepository The deployment repository
     * @param threadExecutor       The thread executor
     * @param postExecutionThread  The post execution thread
     */
    @Inject
    protected ListDeploymentUsecase(DeploymentRepository deploymentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mDeploymentRepository.getEntities();
    }
}
