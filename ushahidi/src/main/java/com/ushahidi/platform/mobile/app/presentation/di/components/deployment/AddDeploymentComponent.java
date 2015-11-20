/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.platform.mobile.app.presentation.di.components.deployment;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.platform.mobile.app.presentation.di.component.AppComponent;
import com.ushahidi.platform.mobile.app.presentation.di.components.AppActivityComponent;
import com.ushahidi.platform.mobile.app.presentation.di.modules.deployment.AddDeploymentModule;
import com.ushahidi.platform.mobile.app.presentation.presenter.deployment.AddDeploymentPresenter;
import com.ushahidi.platform.mobile.app.presentation.view.ui.activity.AddDeploymentActivity;
import com.ushahidi.platform.mobile.app.presentation.view.ui.fragment.AddDeploymentFragment;

import dagger.Component;

/**
 * Provides {@link ActivityScope} based components to {@link AddDeploymentFragment} and the host
 * activity {@link AddDeploymentActivity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class,
        AddDeploymentModule.class})
public interface AddDeploymentComponent extends AppActivityComponent {

    /**
     * Injects {@link AddDeploymentActivity}
     *
     * @param addDeploymentActivity The deployment activity
     */
    void inject(AddDeploymentActivity addDeploymentActivity);

    /**
     * Injects {@link AddDeploymentFragment}
     *
     * @param addDeploymentFragment The deployment fragment
     */
    void inject(AddDeploymentFragment addDeploymentFragment);

    /**
     * Provides {@link AddDeploymentPresenter} to sub-graph
     *
     * @return The add deployment presenter
     */
    AddDeploymentPresenter addDeploymentPresenter();
}
