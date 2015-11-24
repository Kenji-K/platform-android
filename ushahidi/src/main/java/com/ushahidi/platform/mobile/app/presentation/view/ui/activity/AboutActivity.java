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

package com.ushahidi.platform.mobile.app.presentation.view.ui.activity;

import com.ushahidi.platform.mobile.app.R;
import com.ushahidi.platform.mobile.app.presentation.view.ui.fragment.AboutFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * About Activity
 *
 * @author Henry Addo
 */
public class AboutActivity extends BaseAppActivity {

    /**
     * Default constructor
     */
    public AboutActivity() {
        super(R.layout.activity_about, 0);
    }

    /**
     * Provides {@link Intent} launching this activity
     *
     * @param context The calling context
     * @return The intent to be launched
     */
    public static Intent getIntent(final Context context) {
        return new Intent(context, AboutActivity.class);
    }

    private static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String aboutTag = "about_tag";
        AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager()
                .findFragmentByTag(aboutTag);
        if (aboutFragment == null) {
            aboutFragment = AboutFragment.newInstance();
        }
        replaceFragment(R.id.about_fragment_container, aboutFragment, aboutTag);
        if (isTablet(this)) {
            showAsPopup(this);
        }
    }

    /**
     * Show the activity as an popup
     *
     * @param activity The calling activity
     */
    public void showAsPopup(Activity activity) {
        // To show activity as dialog and dim the background, you need to declare
        // android:theme="@style/PopupTheme" on for the chosen activity on the manifest
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.dimAmount = 0.5f;
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            params.height = 700;
        }
        params.width = 680; //fixed width
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        activity.getWindow().setAttributes((params));
    }
}
