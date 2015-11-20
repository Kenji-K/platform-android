package com.ushahidi.platform.mobile.app.data.repository.datasource.formattribute;

import com.ushahidi.platform.mobile.app.data.database.FormAttributeDatabaseHelper;
import com.ushahidi.platform.mobile.app.data.entity.FormAttributeEntity;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributeDatabaseDataSource implements FormAttributeDataSource {

    private final FormAttributeDatabaseHelper mFormAttributeDatabaseHelper;

    public FormAttributeDatabaseDataSource(
            @NonNull FormAttributeDatabaseHelper formAttributeDatabaseHelper) {
        mFormAttributeDatabaseHelper = formAttributeDatabaseHelper;
    }

    @Override
    public Observable<List<FormAttributeEntity>> getFormAttributes(Long deploymentId, Long formId) {
        return mFormAttributeDatabaseHelper.getFormAttributeEntity(deploymentId, formId);
    }
}
