package com.ushahidi.platform.mobile.app.data.api.model;

import com.google.gson.annotations.SerializedName;

import com.ushahidi.platform.mobile.app.data.entity.FormAttributeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributes extends Response implements Serializable {

    private static final long serialVersionUID = 198741968948441199L;

    @SerializedName("results")
    private List<FormAttributeEntity> mFormAttributes;

    public List<FormAttributeEntity> getFormAttributes() {
        return mFormAttributes;
    }
}
