/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.platform.mobile.app.data.database.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.ushahidi.platform.mobile.app.data.entity.AllowedPrivilegesEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostCompletedStagesEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostPublishedToEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostValueEntity;

import java.lang.reflect.Field;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.convert.FieldConverter;
import nl.qbusict.cupboard.convert.ReflectiveEntityConverter;

/**
 * Makes it possible for {@link nl.qbusict.cupboard} to store the raw JSON
 * value for the {@link com.ushahidi.platform.mobile.app.data.entity.PostEntity}
 * value field in the table without deserializing it.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostEntityConverter extends ReflectiveEntityConverter<PostEntity> {

    /**
     * Default constructor
     *
     * @param cupboard The {@link Cupboard} object
     */
    public PostEntityConverter(Cupboard cupboard) {
        super(cupboard, PostEntity.class);
    }

    @Override
    protected FieldConverter<?> getFieldConverter(Field field) {
        if ("mValues".equals(field.getName())) {
            return new PostValueEntityFieldConverter(new TypeToken<PostValueEntity>() {
            }.getType(), new Gson());
        } else if ("mCompletedStages".equals(field.getName())) {
            return new PostCompletedStagesEntityFieldConverter(
                    new TypeToken<PostCompletedStagesEntity>() {
                    }.getType(), new Gson());
        } else if ("mAllowedPrivileges".equals(field.getName())) {
            return new AllowedPrivilegesEntityFieldConverter<>(
                    new TypeToken<AllowedPrivilegesEntity>() {
                    }.getType(), new Gson());
        } else if ("mPublishedTo".equals(field.getName())) {
            return new PostPublishToEntityFieldConverter<>(
                    new TypeToken<PostPublishedToEntity>() {
                    }.getType(), new Gson());
        }
        return super.getFieldConverter(field);
    }
}
