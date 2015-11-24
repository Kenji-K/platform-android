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

package com.ushahidi.platform.mobile.app.presentation.view.ui.form.ui;

import com.ushahidi.platform.mobile.app.presentation.model.FormAttributeModel;
import com.ushahidi.platform.mobile.app.presentation.view.ui.form.wizard.model.PostItemModel;
import com.ushahidi.platform.mobile.app.presentation.view.ui.form.wizard.Screen;
import com.ushahidi.platform.mobile.app.presentation.view.ui.form.wizard.ScreenModelCallbacks;
import com.ushahidi.platform.mobile.app.presentation.view.ui.fragment.AddPostFragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostScreen extends Screen {
    public AddPostScreen(ScreenModelCallbacks callbacks, String title, Long id, Long formId) {
        super(callbacks, title, id, formId);
    }

    @Override
    public Fragment createFragment() {
        return AddPostFragment.newInstance(getKey());
    }

    @Override
    public void getPostItems(ArrayList<PostItemModel> dest) {
        for (FormAttributeModel formAttribute : getAttributes()) {
            dest.add(new PostItemModel(formAttribute.getLabel(), mData.getString(formAttribute.getKey()),
                    formAttribute.getKey(), getKey(), -1));
        }
    }

    @Override
    public boolean isCompleted() {
        if (isRequired()) {
            for (FormAttributeModel formAttribute : getAttributes()) {
                if (formAttribute.getRequired()) {
                    return !TextUtils.isEmpty(mData.getString(formAttribute.getKey()));
                }
            }
        }
        return super.isCompleted();
    }
}
