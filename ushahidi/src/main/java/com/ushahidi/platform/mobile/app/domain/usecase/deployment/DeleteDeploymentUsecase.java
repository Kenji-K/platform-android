package com.ushahidi.platform.mobile.app.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.platform.mobile.app.domain.repository.DeploymentRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case that deletes a  Deployment from the local database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param deploymentRepository The deployment repository
     * @param threadExecutor       The thread executor
     * @param postExecutionThread  The post execution thread
     */
    @Inject
    protected DeleteDeploymentUsecase(DeploymentRepository deploymentRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
    }

    /**
     * Sets deployment Id
     *
     * @param deploymentId The Id of the deployment
     */
    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null) {
            throw new RuntimeException("Deployment ID is null. You must call setDeployment(...)");
        }
        return mDeploymentRepository.deleteEntity(mDeploymentId);
    }
}
