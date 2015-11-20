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

package com.ushahidi.platform.mobile.app.presentation.di.components.deployment;

import com.addhen.android.raiburari.presentation.di.module.ActivityModule;
import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.platform.mobile.app.presentation.di.component.AppComponent;
import com.ushahidi.platform.mobile.app.presentation.di.components.AppActivityComponent;
import com.ushahidi.platform.mobile.app.presentation.di.modules.deployment.DeleteDeploymentModule;
import com.ushahidi.platform.mobile.app.presentation.di.modules.deployment.ListDeploymentModule;
import com.ushahidi.platform.mobile.app.presentation.presenter.deployment.DeleteDeploymentPresenter;
import com.ushahidi.platform.mobile.app.presentation.view.ui.activity.ListDeploymentActivity;
import com.ushahidi.platform.mobile.app.presentation.view.ui.fragment.ListDeploymentFragment;

import dagger.Component;

/**
 * Provides {@link ActivityScope} based components to {@link ListDeploymentFragment} and the host
 * activity {@link ListDeploymentActivity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class,
        ListDeploymentModule.class, DeleteDeploymentModule.class})
public interface DeleteDeploymentComponent extends AppActivityComponent {

    /**
     * Injects dependencies into {@link ListDeploymentActivity}
     *
     * @param listDeploymentActivity The list deployment activity
     */
    void inject(ListDeploymentActivity listDeploymentActivity);

    /**
     * Injects dependencies into {@link ListDeploymentFragment}
     *
     * @param listDeploymentFragment THe list deployment fragment
     */
    void inject(ListDeploymentFragment listDeploymentFragment);

    /**
     * Provides {@link DeleteDeploymentPresenter}
     *
     * @return The delete deployment presenter
     */
    DeleteDeploymentPresenter deleteDeploymentPresenter();
}