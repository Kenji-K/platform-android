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
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class EditTextWidget extends Widget {

    private TextInputLayout mTextInputLayout;

    private AppCompatEditText mInput;

    /**
     * Default constructor that constructs {@link EditTextWidget}
     *
     * @param context The calling context
     * @param name    The name of the form
     * @param label   The label
     */
    public EditTextWidget(Context context, String name, String label,
            FormModelCallbacks callbacks) {
        super(context, name, label, callbacks);
        mTextInputLayout = new TextInputLayout(context);
        mTextInputLayout.setLayoutParams(DEFAULT_LAYOUT_PARAMS);
        mInput = new AppCompatEditText(context);
        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getData().putString(getName(), (editable != null) ? editable.toString() : null);
                notifyDataChanged();
            }
        });
        mInput.setHint(getTitle());
        mTextInputLayout.addView(mInput);
        mInput.setLayoutParams(DEFAULT_LAYOUT_PARAMS);
        addView(mTextInputLayout);
    }

    @Override
    public String getValue() {
        return mInput.getText().toString();
    }

    @Override
    public void setValue(String value) {
        mInput.setText(value);
    }

    @Override
    public void setType(Type type) {
        super.setType(type);
        switch (type) {
            case DATETIME:
                mInput.setInputType(InputType.TYPE_CLASS_DATETIME);
                break;
            case INT:
                mInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                break;
            case LINK:
                mInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
                break;
            default:
                break;

        }
    }

    public AppCompatEditText getInputView() {
        return mInput;
    }

    @Override
    public boolean validate() {
        // Validate if required is set
        if (isRequired()) {
            final String requiredMessage = null;
            RequiredValidator requiredValidator = new RequiredValidator(requiredMessage);
            if (!requiredValidator.isValid(this)) {
                mInput.setError(requiredValidator.getErrorMessage());
                return false;
            }
        }

        for (Validator validator : mValidators) {
            if (!validator.isValid(this)) {
                mInput.setError(validator.getErrorMessage());
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
