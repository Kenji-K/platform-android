package com.ushahidi.platform.mobile.app.data.api.model;

import com.google.gson.annotations.SerializedName;

import com.ushahidi.platform.mobile.app.data.entity.FormEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Forms extends Response implements Serializable {

    private static final long serialVersionUID = -1380430061677564230L;

    @SerializedName("results")
    private List<FormEntity> mForms;

    public List<FormEntity> getForms() {
        return mForms;
    }
}
