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

package com.ushahidi.platform.mobile.app.presentation.view.ui.form;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Form attribute model
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttribute implements Parcelable {

    /**
     * Creates a {@link FormAttribute} parcelable object
     */
    public static final Creator<FormAttribute> CREATOR
            = new Creator<FormAttribute>() {
        @Override
        public FormAttribute createFromParcel(Parcel in) {
            return new FormAttribute(in);
        }

        @Override
        public FormAttribute[] newArray(int size) {
            return new FormAttribute[size];
        }
    };

    private Long _id;

    private String mLabel;

    private String mKey;

    private Input mInput;

    private Type mType;

    private Boolean mRequired;

    private Integer mPriority;

    private List<String> mOptions;

    private Long mDeploymentId;

    private Long mFormId;

    private Integer mCardinality;

    private Long mFormStageId;

    public FormAttribute() {

    }

    protected FormAttribute(Parcel in) {
        _id = in.readByte() == 0x00 ? null : in.readLong();
        mLabel = in.readString();
        mKey = in.readString();
        mInput = (Input) in.readValue(Input.class.getClassLoader());
        mType = (Type) in.readValue(Type.class.getClassLoader());
        byte mRequiredVal = in.readByte();
        mRequired = mRequiredVal == 0x02 ? null : mRequiredVal != 0x00;
        mPriority = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            mOptions = new ArrayList<String>();
            in.readList(mOptions, String.class.getClassLoader());
        } else {
            mOptions = null;
        }
        mDeploymentId = in.readByte() == 0x00 ? null : in.readLong();
        mFormId = in.readByte() == 0x00 ? null : in.readLong();
        mCardinality = in.readByte() == 0x00 ? null : in.readInt();
        mFormStageId = in.readByte() == 0x00 ? null : in.readLong();
    }

    public void setId(Long id) {
        _id = id;
    }

    public Long getId() {
        return _id;
    }

    public Long getFormStageId() {
        return mFormStageId;
    }

    public void setFormStageId(Long formStageId) {
        mFormStageId = formStageId;
    }

    public void setCardinality(Integer cardinality) {
        mCardinality = cardinality;
    }

    public void setFormId(Long formId) {
        mFormId = formId;
    }

    public Long getFormId() {
        return mFormId;
    }

    public void setDeploymentId(Long deploymentId) {
        mDeploymentId = deploymentId;
    }

    public Long getDeploymentId() {
        return mDeploymentId;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
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

    public Boolean getRequired() {
        return mRequired;
    }

    public void setRequired(Boolean required) {
        mRequired = required;
    }

    public Integer getPriority() {
        return mPriority;
    }

    public void setPriority(Integer priority) {
        mPriority = priority;
    }

    public List<String> getOptions() {
        return mOptions;
    }

    public void setOptions(List<String> options) {
        mOptions = options;
    }

    public void setCardinality(int cardinality) {
        mCardinality = cardinality;
    }

    public Integer getCardinality() {
        return mCardinality;
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
        CHECKBOX("checkbox");

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

    @Override
    public String toString() {
        return "FormAttribute{"
                + "mLabel='" + mLabel + '\''
                + ", mKey='" + mKey + '\''
                + ", mInput=" + mInput
                + ", mType=" + mType
                + ", mRequired=" + mRequired
                + ", mPriority=" + mPriority
                + ", mOptions=" + mOptions
                + ",mFormStageId=" + mFormStageId
                + ", mCardinality=" + mCardinality
                + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(_id);
        }
        dest.writeString(mLabel);
        dest.writeString(mKey);
        dest.writeValue(mInput);
        dest.writeValue(mType);
        if (mRequired == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (mRequired ? 0x01 : 0x00));
        }
        if (mPriority == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mPriority);
        }
        if (mOptions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mOptions);
        }
        if (mDeploymentId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mDeploymentId);
        }
        if (mFormId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mFormId);
        }
        if (mCardinality == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mCardinality);
        }
        if (mFormStageId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(mFormStageId);
        }
    }
}