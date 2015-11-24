package com.ushahidi.platform.mobile.app.data.repository.datasource.form;

import com.ushahidi.platform.mobile.app.data.database.FormDatabaseHelper;
import com.ushahidi.platform.mobile.app.data.entity.FormEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormDatabaseDataSource implements FormDataSource {

    private final FormDatabaseHelper mFormDatabaseHelper;

    public FormDatabaseDataSource(@NonNull FormDatabaseHelper formDatabaseHelper) {
        mFormDatabaseHelper = formDatabaseHelper;
    }

    @Override
    public Observable<List<FormEntity>> getForms(Long deploymentId) {
        return mFormDatabaseHelper.getForms(deploymentId);
    }

    @Override
    public Observable<FormEntity> getForm(Long deploymentId, Long formId) {
        return mFormDatabaseHelper.getForm(deploymentId, formId);
    }

    @Override
    public Observable<Long> putForm(FormEntity formEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> deleteForms(Long deploymentId) {
        return mFormDatabaseHelper.deleteForms(deploymentId);
    }

}
