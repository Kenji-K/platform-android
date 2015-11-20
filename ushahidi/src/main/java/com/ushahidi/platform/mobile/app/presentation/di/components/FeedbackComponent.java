/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.platform.mobile.app.presentation.di.components;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.platform.mobile.app.presentation.di.component.AppComponent;
import com.ushahidi.platform.mobile.app.presentation.presenter.FeedbackPresenter;
import com.ushahidi.platform.mobile.app.presentation.view.ui.activity.FeedbackActivity;
import com.ushahidi.platform.mobile.app.presentation.view.ui.fragment.FeedbackFragment;

import dagger.Component;

/**
 * * Provides {@link ActivityScope} based components to {@link FeedbackFragment} and the host
 * activity {@link FeedbackActivity}
 *
 * @author Henry Addo
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface FeedbackComponent extends AppActivityComponent {

    /**
     * Injects dependencies to {@link FeedbackActivity}
     *
     * @param feedbackActivity The feedback activity
     */
    void inject(FeedbackActivity feedbackActivity);

    /**
     * Injects dependencies to {@link FeedbackFragment}
     *
     * @param feedbackFragment The feedback fragment
     */
    void inject(FeedbackFragment feedbackFragment);

    /**
     * Provides {@link FeedbackPresenter} to the sub-graph
     *
     * @return The feedback presenter
     */
    FeedbackPresenter feedbackPresenter();
}
