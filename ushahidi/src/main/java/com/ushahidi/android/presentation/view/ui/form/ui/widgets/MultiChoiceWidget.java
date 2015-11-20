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

package com.ushahidi.android.presentation.view.ui.form.ui.widgets;

import com.ushahidi.android.R;
import com.ushahidi.android.presentation.view.ui.form.FormModelCallbacks;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class MultiChoiceWidget extends Widget {

    private AppCompatTextView mAppCompatTextView;

    private ViewGroup mChoicesContainer;

    private Map<String, String> mChoices;

    private List<String> mValues;

    private Map<String, AppCompatCheckBox> mChoicesView;

    protected MultiChoiceWidget(Context context, String name, String title,
            FormModelCallbacks callbacks) {
        super(context, name, title, callbacks);
        mValues = new ArrayList<>();
        mChoicesView = new HashMap<>();
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.widget_multi_choice, this);

        mAppCompatTextView = (AppCompatTextView) rootView.findViewById(R.id.multi_choice_label);
        mChoicesContainer = (ViewGroup) rootView.findViewById(R.id.multi_choice_container);
    }

    @Override
    public String getValue() {
        return null;
    }

    public List<String> getChoicesValues() {
        for (Map.Entry<String, AppCompatCheckBox> entry : mChoicesView.entrySet()) {
            if (entry.getValue().isChecked()) {
                mValues.add(entry.getKey());
            }
        }
        return mValues;
    }

    public void setChoices(Map<String, String> choices) {
        mChoicesContainer.removeAllViews();
        for (Map.Entry<String, String> entry : choices.entrySet()) {
            AppCompatCheckBox appCompatCheckBox = new AppCompatCheckBox(getContext());
            appCompatCheckBox.setLayoutParams(DEFAULT_LAYOUT_PARAMS);
            appCompatCheckBox.setTag(entry.getKey());
            appCompatCheckBox.setText(entry.getValue());
            mChoicesContainer.addView(appCompatCheckBox);
            mChoicesView.put(entry.getKey(), appCompatCheckBox);
        }
        mChoices = choices;
    }

    @Override
    public void setValue(String value) {
        // Do nothing
    }

    @Override
    public boolean validate() {
        if (isRequired()) {
            if (!mValues.isEmpty()) {
                return true;
            }
        }
        mAppCompatTextView.setError(getContext().getString(R.string.required_field_error));
        return false;
    }

    @Override
    public Widget findByKey(String key) {
        return getName().equals(key) ? this : null;
    }
}
