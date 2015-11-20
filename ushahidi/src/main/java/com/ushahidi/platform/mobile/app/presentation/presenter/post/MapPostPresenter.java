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

package com.ushahidi.platform.mobile.app.presentation.presenter.post;

import com.addhen.android.raiburari.domain.exception.DefaultErrorHandler;
import com.addhen.android.raiburari.domain.exception.ErrorHandler;
import com.addhen.android.raiburari.domain.usecase.DefaultSubscriber;
import com.addhen.android.raiburari.presentation.presenter.Presenter;
import com.ushahidi.platform.mobile.app.data.PrefsFactory;
import com.ushahidi.platform.mobile.app.domain.entity.From;
import com.ushahidi.platform.mobile.app.domain.entity.GeoJson;
import com.ushahidi.platform.mobile.app.domain.usecase.geojson.ListGeoJsonUsecase;
import com.ushahidi.platform.mobile.app.presentation.exception.ErrorMessageFactory;
import com.ushahidi.platform.mobile.app.presentation.model.mapper.GeoJsonModelDataMapper;
import com.ushahidi.platform.mobile.app.presentation.view.post.MapPostView;

import javax.inject.Inject;

/**
 * Presenter for fetching Post with GeoJson
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class MapPostPresenter implements Presenter {

    private final ListGeoJsonUsecase mListGeoJsonUsecase;

    private final GeoJsonModelDataMapper mGeoJsonModelDataMapper;

    private final PrefsFactory mPrefsFactory;

    private MapPostView mMapPostView;

    /**
     * Default constructor
     *
     * @param listGeoJsonUsecase     The list GeoJson use case
     * @param geoJsonModelDataMapper The GeoJson data mapper
     * @param prefsFactory           The prefs factory
     */
    @Inject
    public MapPostPresenter(ListGeoJsonUsecase listGeoJsonUsecase,
            GeoJsonModelDataMapper geoJsonModelDataMapper, PrefsFactory prefsFactory) {
        mListGeoJsonUsecase = listGeoJsonUsecase;
        mGeoJsonModelDataMapper = geoJsonModelDataMapper;
        mPrefsFactory = prefsFactory;
    }

    @Override
    public void resume() {
        loadGeoJsonFromDb();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mListGeoJsonUsecase.unsubscribe();
    }

    public void setView(MapPostView mapPostView) {
        mMapPostView = mapPostView;
    }

    /**
     * Loads {@link com.ushahidi.platform.mobile.app.presentation.model.GeoJsonModel} from local storage
     */
    public void loadGeoJsonFromDb() {
        loadGeoJson(From.DATABASE);
    }

    /**
     * Loads {@link com.ushahidi.platform.mobile.app.presentation.model.GeoJsonModel} from online storage
     */
    public void loadGeoJsonFromOnline() {
        loadGeoJson(From.ONLINE);
    }

    private void loadGeoJson(From from) {
        mListGeoJsonUsecase.setListGeoJson(mPrefsFactory.getActiveDeploymentId().get(), from);
        mListGeoJsonUsecase.execute(new DefaultSubscriber<GeoJson>() {
            @Override
            public void onStart() {
                mMapPostView.hideRetry();
                mMapPostView.showLoading();
            }

            @Override
            public void onCompleted() {
                mMapPostView.hideLoading();
            }

            @Override
            public void onNext(GeoJson geoJsons) {
                mMapPostView.hideLoading();
                mMapPostView.showGeoJson(mGeoJsonModelDataMapper.map(geoJsons));

            }

            @Override
            public void onError(Throwable e) {
                mMapPostView.hideLoading();
                showErrorMessage(new DefaultErrorHandler((Exception) e));
                mMapPostView.showRetry();
            }
        });
    }

    private void showErrorMessage(ErrorHandler errorHandler) {
        String errorMessage = ErrorMessageFactory
                .create(mMapPostView.getAppContext(), errorHandler.getException());
        mMapPostView.showError(errorMessage);
    }
}
