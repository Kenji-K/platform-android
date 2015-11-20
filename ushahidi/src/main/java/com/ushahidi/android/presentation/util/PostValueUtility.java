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

package com.ushahidi.android.presentation.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Utility for generating form value's json string
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class PostValueUtility {

    private JSONObject mJSONObject = new JSONObject();

    private PostValueUtility(JSONObject jsonObject) {
        mJSONObject = jsonObject;
    }

    public String getValue() {
        return mJSONObject.toString();
    }

    public static class Builder {

        private JSONObject mJSONObject = new JSONObject();

        public Builder withArray(String key, List<String> values) {
            JSONArray jsonArray = new JSONArray();
            for (String vals : values) {
                jsonArray.put(vals);
            }
            try {
                mJSONObject.put(key, jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder withObject(String key, Map<String, ?> values) {
            JSONArray jsonArray = new JSONArray();
            for (Map.Entry<String, ?> entry : values.entrySet()) {
                JSONObject json = new JSONObject();
                try {
                    json.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                    jsonArray.put(json);
                }
                jsonArray.put(json);
            }
            try {
                mJSONObject.put(key, jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }

        public PostValueUtility build() {
            return new PostValueUtility(mJSONObject);
        }
    }
}