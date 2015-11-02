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

/**
 * Represents a single line item on the final review page.
 */
public class PostItemModel {

    /**
     * The default weight
     */
    public static final int DEFAULT_WEIGHT = 0;

    private int mWeight;

    private String mTitle;

    private String mDisplayValue;

    private String mFieldKey;

    private String mPageKey;

    public PostItemModel(String title, String displayValue, String fieldKey, String pageKey) {
        this(title, displayValue, fieldKey, pageKey, DEFAULT_WEIGHT);
    }

    public PostItemModel(String title, String displayValue, String fieldKey, String pageKey,
            int weight) {
        mTitle = title;
        mDisplayValue = displayValue;
        mFieldKey = fieldKey;
        mPageKey = pageKey;
        mWeight = weight;
    }

    public String getDisplayValue() {
        return mDisplayValue;
    }

    public void setDisplayValue(String displayValue) {
        mDisplayValue = displayValue;
    }

    public String getPageKey() {
        return mPageKey;
    }

    public void setFieldKey(String fieldKey) {
        mFieldKey = fieldKey;
    }

    public void setPageKey(String pageKey) {
        mPageKey = pageKey;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int weight) {
        mWeight = weight;
    }
}
