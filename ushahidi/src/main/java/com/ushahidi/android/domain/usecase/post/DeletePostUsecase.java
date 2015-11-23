package com.ushahidi.android.domain.usecase.post;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.PostRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case that deletes Posts from the local database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeletePostUsecase extends Usecase {

    private final PostRepository mPostRepository;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param postRepository The post repository
     * @param threadExecutor The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected DeletePostUsecase(PostRepository postRepository,
                                ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mPostRepository = postRepository;
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
        return mPostRepository.deleteDeploymentPosts(mDeploymentId);
    }
}
