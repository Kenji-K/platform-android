/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.view.ui.form.wizard.model;

import com.ushahidi.android.presentation.model.FormAttributeModel;
import com.ushahidi.android.presentation.view.ui.form.ui.widgets.Widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single page in the wizard.
 */
public abstract class Screen implements ScreenTreeNode {

    protected ScreenModelCallbacks mCallbacks;

    /**
     * Current wizard values/selections.
     */
    protected Bundle mData = new Bundle();

    protected List<FormAttributeModel> mAttributeList = new ArrayList<>();

    protected List<Widget> mWidgets = new ArrayList<>();

    protected String mTitle;

    protected Long mFormId;

    protected Long mId;

    protected boolean mRequired = false;


    protected Screen(ScreenModelCallbacks callbacks, String title, Long id, Long formId) {
        mCallbacks = callbacks;
        mTitle = title;
        mId = id;
        mFormId = formId;
    }

    public List<FormAttributeModel> getAttributes() {
        return mAttributeList;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isRequired() {
        return mRequired;
    }

    @Override
    public Screen findByKey(String key) {
        return (String.valueOf(getId()).equals(key)) ? this : null;
    }

    @Override
    public void flattenCurrentScreenSequence(ArrayList<Screen> dest) {
        dest.add(this);
    }

    public abstract Fragment createFragment();

    public Long getId() {
        return mId;
    }

    public Long getFormId() {
        return mFormId;
    }

    public String getKey() {
        return String.valueOf(mId);
    }

    public abstract void getPostItems(ArrayList<PostItem> dest);

    public void setDataBundle(Bundle data) {
        mData = data;
        notifyDataChanged();
    }

    public void setAttributeList(List<FormAttributeModel> attributes) {
        mAttributeList = attributes;
    }

    public void setWidgets(List<Widget> widgets) {
        mWidgets = widgets;
    }

    public List<Widget> getWidgets() {
        return mWidgets;
    }

    public void notifyDataChanged() {
        mCallbacks.onScreenDataChanged(this);
    }

    public Screen setRequired(boolean required) {
        mRequired = required;
        return this;
    }

    public boolean isCompleted() {
        return true;
    }

    public Bundle getDataBundle() {
        return mData;
    }
}
