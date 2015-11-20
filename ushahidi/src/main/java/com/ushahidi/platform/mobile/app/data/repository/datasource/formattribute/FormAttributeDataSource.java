package com.ushahidi.platform.mobile.app.data.repository.datasource.formattribute;

import com.ushahidi.platform.mobile.app.data.entity.FormAttributeEntity;

import java.util.List;

import rx.Observable;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface FormAttributeDataSource {

    /**
     * Get a list of {@link FormAttributeEntity} from the database.
     *
     * @param deploymentId An ID of {@link com.ushahidi.platform.mobile.app.domain.entity.Deployment}
     * @param formId       The id of the form to be fetched
     * @return The form details
     */
    Observable<List<FormAttributeEntity>> getFormAttributes(Long deploymentId, Long formId);
}
