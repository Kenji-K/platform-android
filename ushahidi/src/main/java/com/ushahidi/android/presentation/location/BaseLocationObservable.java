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
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Base class to
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class BaseLocationObservable<T> implements Observable.OnSubscribe<T> {

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 30; // 30 meters

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 2; // 2 minutes

    protected LocationManager mLocationManager;

    private LocationListener mLocationListener;

    private Context mContext;

    protected BaseLocationObservable(Context context) {
        mContext = context;
    }

    protected abstract void onLocationFixed(Observer<? super T> observer);

    public void setLocationListener(LocationListener locationListener) {
        mLocationListener = locationListener;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        onLocationFixed(subscriber);
    }

    protected boolean isGPSEnabled() {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    protected boolean isNetworkEnabled() {
        return mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    protected Location getLasKnowLocation(Observer<? super T> observer) {
        Location location = null;
        try {
            if (isNetworkEnabled()) {
                location = mLocationManager
                        .getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
                Timber.i("NETWORK_PROVIDER %s", "Enabled");
            }

            if (isGPSEnabled() && location == null) {
                location = mLocationManager
                        .getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                Timber.i("GPS_PROVIDER %s", "Enabled");
            }
        } catch (Exception e) {
            observer.onError(e);
        }
        return location;
    }

    protected void getLocationUpdates(Observer<? super T> observer) {
        if (mLocationListener == null) {
            throw new IllegalArgumentException("LocationListener cannot be null");
        }
        try {
            if (isNetworkEnabled()) {
                mLocationManager.requestLocationUpdates(
                        android.location.LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener, Looper.getMainLooper());
                Timber.i("NETWORK_PROVIDER %s", "Enabled");
                return;
            }
            if (isGPSEnabled()) {
                mLocationManager.requestLocationUpdates(
                        android.location.LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener, Looper.getMainLooper());
                Timber.i("GPS_PROVIDER %s", "Enabled");
            }
        } catch (Exception e) {
            observer.onError(e);
        }
    }

    public void stopLocating() {
        if (mLocationManager != null) {
            try {
                mLocationManager.removeUpdates(mLocationListener);
            } catch (Exception ex) {
                Log.e(getClass().getSimpleName(), "stopLocating", ex);
            }
            mLocationManager = null;
        }
    }
}
