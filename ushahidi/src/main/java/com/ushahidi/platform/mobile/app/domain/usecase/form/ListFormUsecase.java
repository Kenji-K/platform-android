package com.ushahidi.platform.mobile.app.domain.usecase.form;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.addhen.android.raiburari.domain.usecase.Usecase;
import com.ushahidi.platform.mobile.app.domain.entity.From;
import com.ushahidi.platform.mobile.app.domain.repository.FormRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Get {@link com.ushahidi.platform.mobile.app.domain.entity.Form} from local storage or via the API
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListFormUsecase extends Usecase {

    private final FormRepository mFormRepository;

    private Long mDeploymentId = null;

    private From mFrom;

    /**
     * Default constructor
     *
     * @param formRepository      The FormJson Repository
     * @param threadExecutor      The thread executor
     * @param postExecutionThread The post execution thread
     */
    @Inject
    protected ListFormUsecase(FormRepository formRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFormRepository = formRepository;
    }

    /**
     * Sets the deployment ID to be used to fetch the {@link com.ushahidi.platform.mobile.app.domain.entity.GeoJson}
     * and where to fetch it from.
     *
     * @param deploymentId The deploymentId associated with the GeoJson
     * @param from         Whether to fetch through the API or the local storage
     */
    public void setListForm(Long deploymentId, From from) {
        mDeploymentId = deploymentId;
        mFrom = from;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mDeploymentId == null || mFrom == null) {
            throw new RuntimeException(
                    "Deployment id and from cannot be null. You must call setListForm(...)");
        }
        return mFormRepository.getForms(mDeploymentId, mFrom);
    }

}
