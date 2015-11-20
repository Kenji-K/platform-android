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

import com.ushahidi.android.presentation.view.ui.form.FormModelCallbacks;
import com.ushahidi.android.presentation.view.ui.form.validator.validator.RequiredValidator;
import com.ushahidi.android.presentation.view.ui.form.validator.validator.Validator;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class MultiChoiceWidget extends Widget {

    private AppCompatTextView mTitle;

    private AppCompatCheckBox mAppCompatCheckBox;

    protected MultiChoiceWidget(Context context, String name, String title,
            FormModelCallbacks callbacks) {
        super(context, name, title, callbacks);
        mAppCompatCheckBox = new AppCompatCheckBox(context);
        mAppCompatCheckBox.setLayoutParams(DEFAULT_LAYOUT_PARAMS);
        mAppCompatCheckBox.setText(title);
        addView(mAppCompatCheckBox);
    }

    @Override
    public String getValue() {
        return String.valueOf(mAppCompatCheckBox.isChecked());
    }

    @Override
    public void setValue(String value) {
        mAppCompatCheckBox.setChecked(Boolean.valueOf(value));
    }

    @Override
    public boolean validate() {
        // Validate if required is set
        if (isRequired()) {
            final String requiredMessage = null;
            RequiredValidator requiredValidator = new RequiredValidator(requiredMessage);
            if (!requiredValidator.isValid(this)) {
                mAppCompatCheckBox.setError(requiredValidator.getErrorMessage());
                return false;
            }
        }
        for (Validator validator : mValidators) {
            if (!validator.isValid(this)) {
                mAppCompatCheckBox.setError(validator.getErrorMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public Widget findByKey(String key) {
        return getName().equals(key) ? this : null;
    }
}
