package com.ushahidi.android.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.android.domain.repository.DeploymentRepository;
import com.ushahidi.android.domain.repository.FormRepository;
import com.ushahidi.android.domain.repository.GeoJsonRepository;
import com.ushahidi.android.domain.repository.PostRepository;
import com.ushahidi.android.domain.repository.TagRepository;
import com.ushahidi.android.domain.repository.UserProfileRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case that deletes a  Deployment from the local database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteDeploymentUsecase extends Usecase {

    private final DeploymentRepository mDeploymentRepository;

    private final PostRepository mPostRepository;

    private final GeoJsonRepository mGeoJsonRepository;

    private final FormRepository mFormRepository;

    private final UserProfileRepository mUserProfileRepository;

    private final TagRepository mTagRepository;

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
                                      PostRepository postRepository,
                                      GeoJsonRepository geoJsonRepository,
                                      FormRepository formRepository,
                                      UserProfileRepository userProfileRepository,
                                      TagRepository tagRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mDeploymentRepository = deploymentRepository;
        mPostRepository = postRepository;
        mGeoJsonRepository = geoJsonRepository;
        mFormRepository = formRepository;
        mUserProfileRepository = userProfileRepository;
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
        mPostRepository.deleteDeploymentPosts(mDeploymentId);
        mGeoJsonRepository.deleteGeoJsonList(mDeploymentId);
        mFormRepository.deleteForms(mDeploymentId);
        mUserProfileRepository.deleteUserProfiles(mDeploymentId);
        mTagRepository.deleteTagList(mDeploymentId);
        return mDeploymentRepository.deleteEntity(mDeploymentId);
    }
}
