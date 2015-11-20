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

package com.ushahidi.platform.mobile.app.presentation.location.geocoder;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class GeocodeObservable implements Observable.OnSubscribe<List<Address>> {

    private final Context mContext;

    private final String mLocationName;

    private final int mMaxResults;

    private GeocodeObservable(Context context, String locationName, int maxResults) {
        mContext = context;
        mLocationName = locationName;
        mMaxResults = maxResults;
    }

    public static Observable<List<Address>> createObservable(Context ctx, String locationName,
            int maxResults) {
        return Observable.create(new GeocodeObservable(ctx, locationName, maxResults));
    }

    @Override
    public void call(Subscriber<? super List<Address>> subscriber) {
        Geocoder geocoder = new Geocoder(mContext);
        List<Address> result = new ArrayList<>();

        try {
            result = geocoder.getFromLocationName(mLocationName, mMaxResults);
        } catch (IOException e) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onError(e);
            }
        }
        if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(result);
            subscriber.onCompleted();
        }
    }
}
