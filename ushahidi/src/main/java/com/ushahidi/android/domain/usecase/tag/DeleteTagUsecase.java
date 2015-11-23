package com.ushahidi.android.domain.usecase.tag;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.TagRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case that deletes Tags from the local database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteTagUsecase extends Usecase {

    private final TagRepository mTagRepository;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param tagRepository The tag repository
     * @param threadExecutor The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected DeleteTagUsecase(TagRepository tagRepository,
                               ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mTagRepository = tagRepository;
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
        return mTagRepository.deleteTagList(mDeploymentId);
    }
}
