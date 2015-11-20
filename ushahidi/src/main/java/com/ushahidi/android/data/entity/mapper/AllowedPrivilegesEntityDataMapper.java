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

package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.data.entity.AllowedPrivilegesEntity;
import com.ushahidi.android.domain.entity.AllowedPrivileges;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Singleton
public class AllowedPrivilegesEntityDataMapper {

    /**
     * Default constructor
     */
    @Inject
    public AllowedPrivilegesEntityDataMapper() {
        // Do nothing
    }

    /**
     * Maps {@link AllowedPrivilegesEntity} onto {@link AllowedPrivileges}
     *
     * @param postEntity The post entity to be mapped
     * @return The post value
     */
    public AllowedPrivileges map(AllowedPrivilegesEntity postEntity) {
        AllowedPrivileges allowPrivilegesEntity = null;
        if (postEntity != null) {
            allowPrivilegesEntity = new AllowedPrivileges();
            allowPrivilegesEntity.setAllowedPrivileges(postEntity.getAllowedPrivileges());
        }
        return allowPrivilegesEntity;
    }

    /**
     * Maps {@link AllowedPrivileges} onto {@link AllowedPrivilegesEntity}
     *
     * @param allowPrivileges The post allowed privileges entity
     * @return The post value
     */
    public AllowedPrivilegesEntity map(AllowedPrivileges allowPrivileges) {
        AllowedPrivilegesEntity allowPrivilegesEntity = null;
        if (allowPrivileges != null) {
            allowPrivilegesEntity = new AllowedPrivilegesEntity();
            allowPrivilegesEntity.setAllowedPrivileges(allowPrivileges.getAllowedPrivileges());
        }
        return allowPrivilegesEntity;
    }

    /**
     * Maps a list {@link AllowedPrivilegesEntity} into a list of {@link AllowedPrivileges}.
     *
     * @param postEntityList List to be mapped.
     * @return {@link AllowedPrivileges}
     */
    public List<AllowedPrivileges> map(List<AllowedPrivilegesEntity> postEntityList) {
        List<AllowedPrivileges> allowPrivilegesEntityList = new ArrayList<>();
        AllowedPrivileges post;
        for (AllowedPrivilegesEntity allowPrivilegesEntityEntity : postEntityList) {
            post = map(allowPrivilegesEntityEntity);
            if (post != null) {
                allowPrivilegesEntityList.add(post);
            }
        }
        return allowPrivilegesEntityList;
    }
}
