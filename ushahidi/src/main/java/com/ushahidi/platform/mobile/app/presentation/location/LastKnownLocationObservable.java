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

package com.ushahidi.platform.mobile.app.presentation.location;

import android.content.Context;
import android.location.Location;

import rx.Observable;
import rx.Observer;

/**
 * Fetches the users known last location
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LastKnownLocationObservable extends BaseLocationObservable<Location> {

    protected LastKnownLocationObservable(Context context) {
        super(context);
    }

    public static Observable<Location> createObservable(Context ctx) {
        return Observable.create(new LastKnownLocationObservable(ctx));
    }

    @Override
    protected void onLocationFixed(Observer<? super Location> observer) {
        Location location = getLasKnowLocation(observer);
        if (location != null) {
            observer.onNext(location);
        }
        observer.onCompleted();
    }
}
