package com.ushahidi.platform.mobile.app.presentation.view.form;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.platform.mobile.app.presentation.model.FormModel;

import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface ListFormView extends LoadDataView {

    /**
     * Render a form list in the UI.
     *
     * @param formModel The collection of {@link FormModel} that will be shown.
     */
    void renderFormList(List<FormModel> formModel);
}
