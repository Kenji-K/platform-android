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

package com.ushahidi.platform.mobile.app.presentation.view.ui.activity;

import com.addhen.android.raiburari.presentation.di.HasComponent;
import com.ushahidi.platform.mobile.app.R;
import com.ushahidi.platform.mobile.app.presentation.di.components.deployment.DaggerDeleteDeploymentComponent;
import com.ushahidi.platform.mobile.app.presentation.di.components.deployment.DaggerListDeploymentComponent;
import com.ushahidi.platform.mobile.app.presentation.di.components.deployment.DeleteDeploymentComponent;
import com.ushahidi.platform.mobile.app.presentation.di.components.deployment.ListDeploymentComponent;
import com.ushahidi.platform.mobile.app.presentation.model.DeploymentModel;
import com.ushahidi.platform.mobile.app.presentation.view.ui.fragment.ListDeploymentFragment;
import com.ushahidi.platform.mobile.app.presentation.view.ui.navigation.Launcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ListDeploymentActivity extends BaseAppActivity
        implements HasComponent<ListDeploymentComponent>,
        ListDeploymentFragment.DeploymentListListener {

    private static final String FRAG_TAG = "list_deployment";

    @Inject
    Launcher mLauncher;

    private ListDeploymentFragment mListDeploymentFragment;

    private ListDeploymentComponent mListDeploymentComponent;

    private DeleteDeploymentComponent mDeleteDeploymentComponent;


    /**
     * Default constructor
     */
    public ListDeploymentActivity() {
        super(R.layout.activity_list_deployment, 0);
    }

    /**
     * Provides {@link Intent} launching this activity
     *
     * @param context The calling context
     * @return The intent to be launched
     */
    public static Intent getIntent(final Context context) {
        return new Intent(context, ListDeploymentActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        mListDeploymentFragment = (ListDeploymentFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG);
        if (mListDeploymentFragment == null) {
            mListDeploymentFragment = ListDeploymentFragment.newInstance();
            replaceFragment(R.id.add_fragment_container, mListDeploymentFragment);
        }
    }

    private void injector() {
        mDeleteDeploymentComponent = DaggerDeleteDeploymentComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();

        mListDeploymentComponent = DaggerListDeploymentComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public ListDeploymentComponent getComponent() {
        return mListDeploymentComponent;
    }

    @SuppressWarnings("unchecked")
    public DeleteDeploymentComponent getDeleteDeploymentComponent() {
        return mDeleteDeploymentComponent;
    }

    @Override
    public void onDeploymentClicked(DeploymentModel deploymentModel) {
        mListDeploymentComponent.launcher().launchUpdateDeployment(deploymentModel);
    }
}