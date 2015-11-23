package com.ushahidi.android.domain.usecase.user;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.UserProfileRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case that deletes UserProfiles from the local database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteUserProfileUsecase extends Usecase {

    private final UserProfileRepository mUserProfileRepository;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param userprofileRepository The userprofile repository
     * @param threadExecutor The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected DeleteUserProfileUsecase(UserProfileRepository userprofileRepository,
                                       ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserProfileRepository = userprofileRepository;
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
        return mUserProfileRepository.deleteUserProfiles(mDeploymentId);
    }
}
