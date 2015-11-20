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

package com.ushahidi.platform.mobile.app.presentation.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.platform.mobile.app.R;
import com.ushahidi.platform.mobile.app.presentation.di.components.deployment.AddDeploymentComponent;
import com.ushahidi.platform.mobile.app.presentation.model.DeploymentModel;
import com.ushahidi.platform.mobile.app.presentation.presenter.deployment.AddDeploymentPresenter;
import com.ushahidi.platform.mobile.app.presentation.validator.UrlValidator;
import com.ushahidi.platform.mobile.app.presentation.view.deployment.AddDeploymentView;
import com.ushahidi.platform.mobile.app.presentation.view.ui.navigation.Launcher;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Fragment for adding a new deployment
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddDeploymentFragment extends BaseFragment implements AddDeploymentView {

    @Bind(R.id.add_deployment_url)
    EditText url;

    @Bind(R.id.add_deployment_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.add_deployment_add)
    Button mAddButton;

    @Bind(R.id.add_deployment_cancel)
    Button mCancelButton;

    @Inject
    AddDeploymentPresenter mAddDeploymentPresenter;

    @Inject
    Launcher mLauncher;

    /**
     * Add Deployment  Fragment
     */
    public AddDeploymentFragment() {
        super(R.layout.fragment_add_deployment, R.menu.add_deployment);
    }

    public static AddDeploymentFragment newInstance() {
        AddDeploymentFragment addDeploymentFragment = new AddDeploymentFragment();
        return addDeploymentFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        url.setOnTouchListener((view, event) -> setHttpProtocol());
    }

    private boolean setHttpProtocol() {
        if (TextUtils.isEmpty(url.getText().toString())) {
            url.setText("http://");
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAddDeploymentPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAddDeploymentPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAddDeploymentPresenter.destroy();
    }

    private void initialize() {
        getComponent(AddDeploymentComponent.class).inject(this);
        mAddDeploymentPresenter.setView(this);
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplication();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @OnClick(R.id.add_deployment_add)
    public void onClickValidate() {
        submit();
    }

    @OnEditorAction(R.id.add_deployment_url)
    boolean onEditorAction(TextView textView, int actionId) {
        if (textView == url) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    submit();
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private void submit() {
        url.setError(null);
        if (!(new UrlValidator().isValid(url.getText().toString()))) {
            url.setError(getString(R.string.validation_message_invalid_url));
            return;
        }
        mAddDeploymentPresenter.submitUrl(url.getText().toString());
    }

    @OnClick(R.id.add_deployment_cancel)
    public void onClickCancel() {
        getActivity().finish();
    }

    @OnClick(R.id.qr_code_scanner)
    public void onQrCodeScannerClick() {
        mLauncher.launchQrcodeReader();
    }

    public void setDeployment(@NonNull DeploymentModel deploymentModel) {
        url.setText(deploymentModel.getUrl());
    }

    @Override
    public void onDeploymentSuccessfullyAdded(Long row) {
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        mAddButton.setVisibility(View.INVISIBLE);
        mCancelButton.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mAddButton.setVisibility(View.VISIBLE);
        mCancelButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetry() {
        // Do nothing
    }

    @Override
    public void hideRetry() {
        // Do nothing
    }

}