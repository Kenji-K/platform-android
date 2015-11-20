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

package com.ushahidi.platform.mobile.app.data.entity;

import com.ushahidi.platform.mobile.app.BuildConfig;
import com.ushahidi.platform.mobile.app.DefaultConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link DeploymentEntity}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class DeploymentEntityTest {

    private static final Long DUMMY_ID = 1l;

    private static final String DUMMY_TITLE = "Dummy Deployment Title";

    private static final DeploymentEntity.Status DUMMY_STATUS = DeploymentEntity.Status.DEACTIVATED;

    private static final String DUMMY_URL = "http://deployment.com";

    private DeploymentEntity mDeploymentEntity;

    @Before
    public void setUp() {
        mDeploymentEntity = new DeploymentEntity();
    }

    @Test
    public void shouldSetDeploymentEntity() {
        mDeploymentEntity._id = DUMMY_ID;
        mDeploymentEntity.setTitle(DUMMY_TITLE);
        mDeploymentEntity.setStatus(DUMMY_STATUS);
        mDeploymentEntity.setUrl(DUMMY_URL);

        assertThat(mDeploymentEntity).isNotNull();
        assertThat(mDeploymentEntity).isInstanceOf(DeploymentEntity.class);
        assertThat(mDeploymentEntity._id).isNotNull();
        assertThat(mDeploymentEntity._id).isEqualTo(DUMMY_ID);
        assertThat(mDeploymentEntity.getTitle()).isNotNull();
        assertThat(mDeploymentEntity.getTitle()).isEqualTo(DUMMY_TITLE);
        assertThat(mDeploymentEntity.getStatus()).isNotNull();
        assertThat(mDeploymentEntity.getStatus()).isEqualTo(DUMMY_STATUS);
        assertThat(mDeploymentEntity.getUrl()).isNotNull();
        assertThat(mDeploymentEntity.getUrl()).isEqualTo(DUMMY_URL);
    }
}
