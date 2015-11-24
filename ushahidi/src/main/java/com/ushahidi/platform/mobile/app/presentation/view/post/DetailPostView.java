package com.ushahidi.platform.mobile.app.presentation.view.post;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.platform.mobile.app.presentation.model.PostModel;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface DetailPostView extends LoadDataView {

    /**
     * Renders {@link com.ushahidi.platform.mobile.app.presentation.model.PostModel}
     *
     * @param postModel The PostModel
     */
    void showPostModel(PostModel postModel);
}
