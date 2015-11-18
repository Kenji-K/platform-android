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

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class PostValueTest {

    private String sampleJsonResult
            = "{\"test_varchar\":[\"special-string\"],\"last_location_point\":[{\"lon\":1},{\"lat\":1}]}";

    @Test
    public void testFormJsonStringGeneration() {
        List<String> values = new ArrayList<>();
        values.add("special-string");

        Map<String, Integer> data = new HashMap<>();
        data.put("lon", 1);
        data.put("lat", 1);

        PostValueUtility postValue = new PostValueUtility.Builder()
                .withArray("test_varchar", values)
                .withObject("last_location_point", data)
                .build();

        String jsonString = postValue.getValue();
        assertThat(jsonString).isNotNull();
        assertThat(jsonString).isEqualTo(sampleJsonResult);
    }
}