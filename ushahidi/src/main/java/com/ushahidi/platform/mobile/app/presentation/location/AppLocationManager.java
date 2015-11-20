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


import com.ushahidi.platform.mobile.app.presentation.location.geocoder.GeocodeObservable;
import com.ushahidi.platform.mobile.app.presentation.location.geocoder.ReverseGeocodeObservable;

import android.content.Context;
import android.location.Address;
import android.location.Location;

import java.util.List;

import rx.Observable;


/**
 * Location manager that returns observables for getting the location of the user's device.
 * You can reverse geocode to get the location name based on latitude and longitude and vice versa
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AppLocationManager {

    private final Context mContext;

    public AppLocationManager(Context context) {
        mContext = context;
    }

    public Observable<Location> getLastKnownLocation() {
        return LastKnownLocationObservable.createObservable(mContext);
    }

    public Observable<Location> getUpdatedLocation() {
        return LocationUpdatesObservable.createObservable(mContext);
    }

    public Observable<List<Address>> getReverseGeocode(double latitude, double longitude,
            int maxResults) {
        return ReverseGeocodeObservable.createObservable(mContext, latitude, longitude, maxResults);
    }

    public Observable<List<Address>> getGeocode(String locationName, int maxResults) {
        return GeocodeObservable.createObservable(mContext, locationName, maxResults);
    }
}
