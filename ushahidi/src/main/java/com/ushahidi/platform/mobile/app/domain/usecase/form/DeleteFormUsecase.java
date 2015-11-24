package com.ushahidi.platform.mobile.app.domain.usecase.form;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.platform.mobile.app.domain.repository.FormRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case that deletes Forms from the local database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteFormUsecase extends Usecase {

    private final FormRepository mFormRepository;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param formRepository The form repository
     * @param threadExecutor The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected DeleteFormUsecase(FormRepository formRepository,
                                ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFormRepository = formRepository;
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
        return mFormRepository.deleteForms(mDeploymentId);
    }
}
