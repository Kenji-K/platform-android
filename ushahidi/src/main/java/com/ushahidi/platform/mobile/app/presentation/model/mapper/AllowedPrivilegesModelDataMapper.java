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

package com.ushahidi.platform.mobile.app.presentation.model.mapper;

import com.ushahidi.platform.mobile.app.domain.entity.AllowedPrivileges;
import com.ushahidi.platform.mobile.app.presentation.model.AllowedPrivilegesModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AllowedPrivilegesModelDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public AllowedPrivilegesModelDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link AllowedPrivilegesModel} onto {@link AllowedPrivileges}
     *
     * @param postModel The post entity to be mapped
     * @return The post value
     */
    public AllowedPrivileges map(@NonNull AllowedPrivilegesModel postModel) {
        AllowedPrivileges allowPrivilegesModel = null;
        if (postModel != null) {
            allowPrivilegesModel = new AllowedPrivileges();
            allowPrivilegesModel.setAllowedPrivileges(postModel.getAllowedPrivileges());
        }
        return allowPrivilegesModel;
    }

    /**
     * Maps {@link AllowedPrivileges} onto {@link AllowedPrivilegesModel}
     *
     * @param allowPrivileges The post allowed privileges entity
     * @return The post value
     */
    public AllowedPrivilegesModel map(@NonNull AllowedPrivileges allowPrivileges) {
        AllowedPrivilegesModel allowPrivilegesModel = null;
        if (allowPrivileges != null) {
            allowPrivilegesModel = new AllowedPrivilegesModel();
            allowPrivilegesModel.setAllowedPrivileges(allowPrivileges.getAllowedPrivileges());
        }
        return allowPrivilegesModel;
    }

    /**
     * Maps a list {@link AllowedPrivilegesModel} into a list of {@link AllowedPrivileges}.
     *
     * @param postModelList List to be mapped.
     * @return {@link AllowedPrivileges}
     */
    public List<AllowedPrivileges> map(List<AllowedPrivilegesModel> postModelList) {
        List<AllowedPrivileges> allowPrivilegesModelList = new ArrayList<>();
        AllowedPrivileges post;
        for (AllowedPrivilegesModel allowPrivilegesModelModel : postModelList) {
            post = map(allowPrivilegesModelModel);
            if (post != null) {
                allowPrivilegesModelList.add(post);
            }
        }
        return allowPrivilegesModelList;
    }
}
