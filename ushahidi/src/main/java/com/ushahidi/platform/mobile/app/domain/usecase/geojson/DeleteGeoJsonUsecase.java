package com.ushahidi.platform.mobile.app.domain.usecase.geojson;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.platform.mobile.app.domain.repository.GeoJsonRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Use case that deletes GeoJsons from the local database.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteGeoJsonUsecase extends Usecase {

    private final GeoJsonRepository mGeoJsonRepository;

    private Long mDeploymentId;

    /**
     * Default constructor
     *
     * @param geojsonRepository The geojson repository
     * @param threadExecutor The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected DeleteGeoJsonUsecase(GeoJsonRepository geojsonRepository,
                                   ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mGeoJsonRepository = geojsonRepository;
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
        return mGeoJsonRepository.deleteGeoJsonList(mDeploymentId);
    }
}
