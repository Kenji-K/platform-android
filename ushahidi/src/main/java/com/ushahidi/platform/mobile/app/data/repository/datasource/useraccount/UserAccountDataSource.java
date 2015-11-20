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

package com.ushahidi.platform.mobile.app.data.repository.datasource.useraccount;

import com.ushahidi.platform.mobile.app.data.entity.UserAccountEntity;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import rx.Observable;

/**
 * Data source for user account
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface UserAccountDataSource {

    /**
     * Login a {@link com.ushahidi.platform.mobile.app.data.entity.UserEntity}
     *
     * @param userAccountEntity The user to be logged.
     * @return the access token
     */
    Observable<OAuth2AccessToken> loginUserAccountEntity(UserAccountEntity userAccountEntity);
}
