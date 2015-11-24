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

package com.ushahidi.platform.mobile.app.presentation.view.ui.form.validator.validator;


import com.ushahidi.platform.mobile.app.presentation.view.ui.form.ui.widgets.Widget;

import android.text.TextUtils;

/**
 * Validates for required fields. If a field has been marked required, use this class to make sure
 * the field is filled.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class RequiredValidator extends Validator {

    /**
     * Default constructor
     *
     * @param errorMessage The error message
     */
    public RequiredValidator(String errorMessage) {
        super(errorMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Widget formWidget) {
        return TextUtils.getTrimmedLength(formWidget.getValue()) > 0;
    }
}
