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

package com.ushahidi.android.data.database.converter;

import com.google.gson.Gson;

import android.content.ContentValues;
import android.database.Cursor;

import java.lang.reflect.Type;

import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.FieldConverter;

/**
 * Makes it possible for {@link nl.qbusict.cupboard} to store the raw JSON
 * allow_permission for the {@link com.ushahidi.android.data.entity.PostEntity}
 * value field in the table without de-serializing it.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AllowedPermissionEntityFieldConverter<T> implements FieldConverter<T> {

    private final Gson mGson;

    private final Type mType;

    /**
     * Default constructor
     *
     * @param type The type
     * @param gson The gson
     */
    public AllowedPermissionEntityFieldConverter(Type type, Gson gson) {
        mType = type;
        mGson = gson;
    }

    @Override
    public T fromCursorValue(Cursor cursor, int index) {
        return mGson.fromJson(cursor.getString(index), mType);
    }

    @Override
    public void toContentValue(T value, String key, ContentValues values) {
        values.put(key, mGson.toJson(value));
    }

    @Override
    public EntityConverter.ColumnType getColumnType() {
        return EntityConverter.ColumnType.TEXT;
    }

}
