package com.ushahidi.platform.mobile.app.data.repository;

import com.ushahidi.platform.mobile.app.data.entity.mapper.FormStageEntityDataMapper;
import com.ushahidi.platform.mobile.app.data.repository.datasource.formstage.FormStageDataSource;
import com.ushahidi.platform.mobile.app.data.repository.datasource.formstage.FormStageDataSourceFactory;
import com.ushahidi.platform.mobile.app.domain.entity.FormStage;
import com.ushahidi.platform.mobile.app.domain.entity.From;
import com.ushahidi.platform.mobile.app.domain.repository.FormStageRepository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStageDataRepository implements FormStageRepository {

    private final FormStageDataSourceFactory mFormStageDataSourceFactory;

    private final FormStageEntityDataMapper mFormStageEntityDataMapper;

    /**
     * Default constructor that creates {@link FormDataRepository}
     *
     * @param formStageDataSourceFactory A factory to create the different data source
     *                                   implementations
     * @param formStageEntityDataMapper  The @{link FormEntityDataMapper}
     */
    @Inject
    public FormStageDataRepository(
            @NonNull FormStageDataSourceFactory formStageDataSourceFactory,
            FormStageEntityDataMapper formStageEntityDataMapper) {
        mFormStageDataSourceFactory = formStageDataSourceFactory;
        mFormStageEntityDataMapper = formStageEntityDataMapper;
    }

    @Override
    public Observable<List<FormStage>> getFormStages(Long deploymentId, Long formId,
            From from) {
        FormStageDataSource formStageDataSource;
        if (from == From.ONLINE) {
            formStageDataSource = mFormStageDataSourceFactory.createApiDataSource();
        } else {
            formStageDataSource = mFormStageDataSourceFactory
                    .createDatabaseDataSource();
        }
        return formStageDataSource.getFormStages(deploymentId, formId)
                .map(mFormStageEntityDataMapper::map);
    }

    @Override
    public Observable<Long> putFormStage(FormStage form) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<FormStage> getFormStage(Long deploymentId, Long formStageId) {
        throw new UnsupportedOperationException();
    }
}

