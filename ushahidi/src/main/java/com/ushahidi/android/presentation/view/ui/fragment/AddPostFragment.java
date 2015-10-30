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

package com.ushahidi.android.presentation.view.ui.fragment;

import com.addhen.android.raiburari.presentation.ui.fragment.BaseFragment;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.model.FormAttributeModel;
import com.ushahidi.android.presentation.model.TagModel;
import com.ushahidi.android.presentation.view.ui.form.FormModelCallbacks;
import com.ushahidi.android.presentation.view.ui.form.ScreenFragmentCallbacks;
import com.ushahidi.android.presentation.view.ui.form.ui.AddPostScreen;
import com.ushahidi.android.presentation.view.ui.form.ui.widgets.EditTextWidget;
import com.ushahidi.android.presentation.view.ui.form.ui.widgets.LocationWidget;
import com.ushahidi.android.presentation.view.ui.form.ui.widgets.Widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;

/**
 * Fragment for adding a new post
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostFragment extends BaseFragment implements FormModelCallbacks {

    private static final String ARGUMENT_KEY_SCREEN
            = "com.ushahidi.android.ARGUMENT_KEY_SCREEN";

    @Bind(R.id.form_attributes)
    ViewGroup mFormAttributeViewGroup;

    @Bind(R.id.categories)
    ViewGroup mCategories;

    private ScreenFragmentCallbacks mCallbacks;

    private String mKey;

    private AddPostScreen mScreen;

    private List<FormAttributeModel> mFormAttributeModelList;

    private List<TagModel> mTagModelList;

    private LocationWidget mLocationWidget;

    /**
     * Add Deployment  Fragment
     */
    public AddPostFragment() {
        super(R.layout.fragment_add_post, R.menu.add_deployment);
    }

    public static AddPostFragment newInstance(String key) {
        AddPostFragment addPostFragment = new AddPostFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_KEY_SCREEN, key);
        addPostFragment.setArguments(arguments);
        return addPostFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        initializeFormAttributeView(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof ScreenFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (ScreenFragmentCallbacks) activity;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLocationWidget != null) {
            mLocationWidget.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;

    }

    private void initialize() {
        Bundle args = getArguments();
        mKey = args.getString(ARGUMENT_KEY_SCREEN);
        mScreen = (AddPostScreen) mCallbacks.onGetScreen(mKey);
    }

    private void initializeFormAttributeView(Bundle savedInstanceState) {
        mScreen = (AddPostScreen) mCallbacks.onGetScreen(mKey);
        if (mScreen != null) {
            mFormAttributeModelList = mScreen.getAttributes();
            mFormAttributeViewGroup.removeAllViews();
            if ((mFormAttributeModelList != null) && (!mFormAttributeModelList.isEmpty())) {
                Collections.sort(mFormAttributeModelList, new Priority());
                for (FormAttributeModel formAttributeModel : mFormAttributeModelList) {
                    if (FormAttributeModel.Input.TEXT.equals(formAttributeModel.getInput())) {
                        EditTextWidget editTextWidget = new EditTextWidget(getContext(),
                                formAttributeModel.getKey(),
                                formAttributeModel.getLabel(), this);
                        final String value = mScreen.getDataBundle().getString(
                                formAttributeModel.getKey());
                        editTextWidget.setRequired(formAttributeModel.getRequired());
                        editTextWidget.setValue(value);
                        mScreen.getWidgets().add(editTextWidget);
                        mFormAttributeViewGroup.addView(editTextWidget);
                    } else if (FormAttributeModel.Input.LOCATION
                            .equals(formAttributeModel.getInput())) {
                        mLocationWidget = new LocationWidget(savedInstanceState, getContext(),
                                formAttributeModel.getKey(), formAttributeModel.getLabel(), this);
                        mLocationWidget.setRequired(formAttributeModel.getRequired());
                        final String value = mScreen.getDataBundle().getString(
                                formAttributeModel.getKey());
                        mLocationWidget.setValue(value);
                        mScreen.getWidgets().add(mLocationWidget);
                        mFormAttributeViewGroup.addView(mLocationWidget);
                    }
                }
            }
        }
    }

    @Override
    public void onWidgetDataChanged(Widget widget) {
        mScreen.getDataBundle().putString(widget.getName(), widget.getValue());
        mScreen.notifyDataChanged();
    }

    private class Priority implements Comparator<FormAttributeModel> {

        public int compare(FormAttributeModel item, FormAttributeModel formAttributeModel) {
            if (formAttributeModel != null) {
                return item.getPriority() > formAttributeModel.getPriority() ? 1 : -1;
            }
            return -1;
        }
    }
}
