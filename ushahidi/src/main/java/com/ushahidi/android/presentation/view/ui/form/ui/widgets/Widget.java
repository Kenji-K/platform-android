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
import com.ushahidi.android.presentation.view.ui.form.validator.validator.Validator;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class Widget extends LinearLayout implements Form {

    protected static final LayoutParams DEFAULT_LAYOUT_PARAMS
            = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);

    protected int mPriority;

    protected List<Validator> mValidators = new ArrayList<>();

    protected FormModelCallbacks mCallbacks;

    protected Input mInput;

    protected Type mType;

    protected Bundle mData = new Bundle();

    protected String mTitle;

    protected String mName;

    protected Context mContext;

    // Validation
    private boolean mRequired = false;

    protected Widget(Context context, String name, String title, FormModelCallbacks callbacks) {
        super(context);
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(DEFAULT_LAYOUT_PARAMS);
        mCallbacks = callbacks;
        mTitle = title;
        mName = name;
        setTag(mName);
    }

    public abstract String getValue();

    public abstract void setValue(String value);

    public abstract boolean validate();

    public Bundle getData() {
        return mData;
    }


    public void setVisibility(int visibility) {
        setVisibility(visibility);
    }

    public void addValidator(Validator validator) {
        mValidators.add(validator);
    }

    public List<Validator> getValidators() {
        return mValidators;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public String getName() {
        return mName;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isRequired() {
        return mRequired;
    }

    public void setRequired(boolean required) {
        mRequired = required;
    }

    public Input getInput() {
        return mInput;
    }

    public void setInput(Input input) {
        mInput = input;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public void resetData(Bundle data) {
        mData = data;
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        mCallbacks.onWidgetDataChanged(this);
    }

    public enum Input {

        /**
         * A map widget or input type
         */
        LOCATION("location"),

        /**
         * A text input field
         */
        TEXT("text"),

        /**
         * A drop down select input type
         */
        SELECT("select"),

        /**
         * Date picker
         */
        DATETIME("datetime"),

        /**
         * Textarea input type
         */
        TEXTAREA("textarea"),

        /**
         * Int type
         */
        NUMBER("number"),

        /**
         * Radio input type
         */
        RADIO("radio"),
        /**
         * Date input type
         */
        DATE("date"),

        /**
         * Checkbox input type
         */
        CHECKBOX("checkbox"),

        /**
         * Multichoice input type
         */
        MULTICHOICE("multichoice"),

        /**
         * SINGLE CHOICE
         */
        SINGLECHOICE("singlechoice");

        private String value;

        Input(String value) {
            this.value = value;
        }

        /**
         * Gets value
         *
         * @return The value
         */
        public String getValue() {
            return value;
        }
    }

    public enum Type {

        /**
         * A Varchar type
         */
        VARCHAR("varchar"),

        /**
         * A point type
         */
        POINT("point"),

        /**
         * A datetime type
         */
        DATETIME("datetime"),

        /**
         * A text type
         */
        TEXT("text"),

        /**
         * A geometry type
         */
        GEOMETRY("geometry"),

        /**
         * Integer type
         */
        INT("int"),

        /**
         * Link
         */
        LINK("link");

        private String value;

        Type(String value) {
            this.value = value;
        }

        /**
         * Gets value
         *
         * @return The value
         */
        public String getValue() {
            return value;
        }
    }
}
