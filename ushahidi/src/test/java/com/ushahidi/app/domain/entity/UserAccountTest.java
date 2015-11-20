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

package com.ushahidi.platform.mobile.app.domain.entity;

import com.ushahidi.platform.mobile.app.BuildConfig;
import com.ushahidi.platform.mobile.app.DefaultConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link UserAccount}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class UserAccountTest {

    private static final Long DUMMY_ID = 1l;

    private UserAccount mUserAccount;

    private String ACCOUNT_NAME = "account name";

    private String PASSWORD = "password";

    private String AUTH_TOKEN = "Auth token";

    private String AUTH_TOKEN_TYPE = "auth token type";

    private long DEPLOYMENT_ID = 1l;

    @Before
    public void setUp() {
        mUserAccount = new UserAccount();
    }

    @Test
    public void shouldSetUserAccount() {
        mUserAccount._id = DUMMY_ID;
        mUserAccount.setPassword(PASSWORD);
        mUserAccount.setAccountName(ACCOUNT_NAME);

        assertThat(mUserAccount).isNotNull();
        assertThat(mUserAccount).isInstanceOf(UserAccount.class);
        assertThat(mUserAccount._id).isEqualTo(DUMMY_ID);
        assertThat(mUserAccount.getAccountName()).isNotNull();
        assertThat(mUserAccount.getAccountName()).isEqualTo(ACCOUNT_NAME);
        assertThat(mUserAccount.getPassword()).isNotNull();
        assertThat(mUserAccount.getPassword()).isEqualTo(PASSWORD);
    }
}
