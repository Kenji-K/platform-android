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
import com.ushahidi.android.presentation.model.FormStageModel;
import com.ushahidi.android.presentation.view.ui.form.ui.AddPostScreen;
import com.ushahidi.android.presentation.view.ui.form.wizard.ScreenList;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostFormWizardModel extends AbstractScreenModel {

    private List<FormStageModel> mPages = new ArrayList<>();

    private List<FormAttributeModel> mFormAttributes = new ArrayList<>();

    public PostFormWizardModel(Context context, List<FormStageModel> stages,
            List<FormAttributeModel> formAttributes) {
        super(context);
        mPages = stages;
        mFormAttributes = formAttributes;
        mRootScreenList = onNewRootScreenList();
    }

    @Override
    protected ScreenList onNewRootScreenList() {
        ScreenList screenList = new ScreenList();
        if (mPages != null) {
            int firstItem = 0;
            for (FormStageModel formStage : mPages) {
                AddPostScreen addPostScreen = new AddPostScreen(this, formStage.getLabel(),
                        formStage._id, formStage.getFormId());
                for (FormAttributeModel formAttribute : mFormAttributes) {
                    if (formStage._id == formAttribute.getFormStageId()) {
                        addPostScreen.getAttributes().add(formAttribute);
                    }
                }
                if (firstItem == 0) {
                    addPostScreen.setFirstScreen(true);
                    firstItem++;
                }
                addPostScreen.setRequired(formStage.getRequired());
                screenList.add(addPostScreen);
            }
        }
        return screenList;
    }
}
