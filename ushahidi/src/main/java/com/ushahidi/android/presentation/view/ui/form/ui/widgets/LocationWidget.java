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

package com.ushahidi.android.presentation.view.ui.form.ui.widgets;

import com.ushahidi.android.R;
import com.ushahidi.android.presentation.location.AppLocationManager;
import com.ushahidi.android.presentation.view.ui.form.FormModelCallbacks;
import com.ushahidi.android.presentation.view.ui.form.validator.validator.RequiredValidator;
import com.ushahidi.android.presentation.view.ui.form.validator.validator.Validator;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Location widget that users the phone's GPS or the network to find the current
 * location of the user.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class LocationWidget extends Widget {

    private AppCompatTextView mLabel;

    private AppCompatEditText mLocationName;

    private AppCompatEditText mLocationLatitude;

    private AppCompatEditText mLocationLongitude;

    private AppCompatImageButton mImageButton;

    private ProgressBar mProgressBar;

    private AppLocationManager mAppLocationManager;

    private Observable<Address> mAddressObservable;

    private Subscription mSubscription = Subscriptions.empty();

    public LocationWidget(Context context, String name, String title,
            FormModelCallbacks callbacks) {
        super(context, name, title, callbacks);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.widget_location, this);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.search_location_progress);
        mLabel = (AppCompatTextView) rootView.findViewById(R.id.location_name_label);
        mLocationName = (AppCompatEditText) rootView.findViewById(R.id.field_location_name);
        mLocationLatitude = (AppCompatEditText) rootView.findViewById(R.id.field_latitude);
        mLocationLongitude = (AppCompatEditText) rootView.findViewById(R.id.field_longitude);
        mImageButton = (AppCompatImageButton) rootView.findViewById(R.id.clear_search_icon);
        mImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSearchField();
            }
        });

        final Button button = (AppCompatButton) rootView.findViewById(R.id.find_search_icon);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeLocation();
            }
        });
        mAppLocationManager = new AppLocationManager(mContext);
    }

    private void initializeLocation() {
        mAddressObservable = mAppLocationManager.getUpdatedLocation()
                .flatMap(new Func1<Location, Observable<List<Address>>>() {
                    @Override
                    public Observable<List<Address>> call(Location location) {
                        return mAppLocationManager.getReverseGeocode(location.getLatitude(),
                                location.getLongitude(), 1);
                    }
                })
                .map(new Func1<List<Address>, Address>() {
                    @Override
                    public Address call(List<Address> addresses) {
                        return addresses != null && !addresses.isEmpty() ? addresses.get(0) : null;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        mSubscription = mAddressObservable.subscribe(new DefaultSubscriber<Address>() {

            @Override
            public void onStart() {
                mProgressBar.setVisibility(VISIBLE);
            }

            @Override
            public void onCompleted() {
                mProgressBar.setVisibility(GONE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mProgressBar.setVisibility(GONE);
            }

            @Override
            public void onNext(Address address) {
                if (address != null) {
                    mLocationLatitude.setText(String.valueOf(address.getLatitude()));
                    mLocationLongitude.setText(String.valueOf(address.getLongitude()));
                    final String locationName = String.format("%s, %s, %s, %s",
                            address.getThoroughfare(),
                            address.getSubLocality(),
                            address.getLocality(), address.getCountryName());
                    mLocationName.setText(locationName);
                }
                mProgressBar.setVisibility(GONE);
            }
        });
    }

    public void unsubscribe() {
        if (mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    public void clearSearchField() {
        mLocationName.setText(null);
        mLocationName.requestFocus();

        mLocationLatitude.setText(null);
        mLocationLatitude.requestFocus();

        mLocationLongitude.setText(null);
        mLocationLongitude.requestFocus();
    }

    @Override
    public String getValue() {
        return mLocationName.toString() + ":" + mLocationLongitude.toString() + ":"
                + mLocationLatitude.getText().toString();
    }

    /**
     * Sets the value for the location widget
     *
     * @param value The value should be in the format location name:latitude:longitude
     */
    @Override
    public void setValue(String value) {
        final String[] location = value.split(":");
        if (location != null & location.length > 0) {
            mLocationName.setText(location[0]);
            mLocationLatitude.setText(location[1]);
            mLocationLongitude.setText(location[2]);
        }
    }

    @Override
    public boolean validate() {
        if (isRequired()) {
            final String requiredMessage = null;
            RequiredValidator requiredValidator = new RequiredValidator(requiredMessage);
            if (!requiredValidator.isValid(this)) {
                mLocationName.setError(requiredValidator.getErrorMessage());
                return false;
            }
        }

        for (Validator validator : mValidators) {
            if (!validator.isValid(this)) {
                mLocationName.setError(validator.getErrorMessage());
                return false;
            }
        }

        return true;
    }

    @Override
    public Widget findByKey(String key) {
        return getName().equals(key) ? this : null;
    }

    public class DefaultSubscriber<T> extends rx.Subscriber<T> {

        @Override
        public void onCompleted() {
            // no-op by default.
        }

        @Override
        public void onError(Throwable e) {
            // no-op by default.
        }

        @Override
        public void onNext(T t) {
            // no-op by default.
        }
    }
}
