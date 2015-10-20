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

package com.ushahidi.android.presentation.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import rx.Observable;
import rx.Observer;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LocationUpdatesObservable extends BaseLocationObservable<Location> {

    protected LocationUpdatesObservable(Context context) {
        super(context);
    }

    public static Observable<Location> createObservable(Context context) {
        return Observable.create(new LocationUpdatesObservable(context));
    }

    @Override
    protected void onLocationFixed(final Observer<? super Location> observer) {
        setLocationListener(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    observer.onNext(location);
                }
                stopLocating();
                observer.onCompleted();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        getLocationUpdates(observer);
    }
}
