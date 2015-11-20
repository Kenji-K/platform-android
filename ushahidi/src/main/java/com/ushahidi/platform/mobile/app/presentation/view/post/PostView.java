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

package com.ushahidi.platform.mobile.app.presentation.view.post;

import com.addhen.android.raiburari.presentation.ui.view.LoadDataView;
import com.ushahidi.platform.mobile.app.presentation.model.DeploymentModel;
import com.ushahidi.platform.mobile.app.presentation.model.UserProfileModel;

import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface PostView extends LoadDataView {

    /**
     * Sets the active user's profile
     *
     * @param userProfile The details of the user profile
     */
    void setActiveUserProfile(UserProfileModel userProfile);

    /**
     * Sets the active deployment
     *
     * @param deployment The deployment model
     */
    void setActiveDeployment(DeploymentModel deployment);

    /**
     * Displays deployment list so users can select a deployment then login.
     *
     * @param deploymentModels The {@link DeploymentModel} to be listed
     */
    void deploymentList(List<DeploymentModel> deploymentModels);
}
