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

package com.ushahidi.android.presentation.view.ui.form.wizard.model;

import com.ushahidi.android.presentation.view.ui.form.wizard.Screen;
import com.ushahidi.android.presentation.view.ui.form.wizard.ScreenList;
import com.ushahidi.android.presentation.view.ui.form.wizard.ScreenModelCallbacks;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a wizard model, including the pages/steps in the wizard, their dependencies, and
 * their currently populated choices/values/selections.
 *
 * To create an actual wizard model, extend this class and implement {@link
 * #onNewRootScreenList()}.
 */
public abstract class AbstractScreenModel implements ScreenModelCallbacks {

    protected Context mContext;

    protected ScreenList mRootScreenList;

    private List<ScreenModelCallbacks> mListeners = new ArrayList<>();

    public AbstractScreenModel(Context context) {
        mContext = context;
        mRootScreenList = onNewRootScreenList();
    }

    /**
     * Override this to define a new wizard model.
     */
    protected abstract ScreenList onNewRootScreenList();

    @Override
    public void onScreenDataChanged(Screen page) {
        // can't use for each because of concurrent modification (review fragment
        // can get added or removed and will register itself as a listener)
        for (int i = 0; i < mListeners.size(); i++) {
            mListeners.get(i).onScreenDataChanged(page);
        }
    }

    public Screen findByKey(String key) {
        return mRootScreenList.findByKey(key);
    }

    public void load(Bundle savedValues) {
        for (String key : savedValues.keySet()) {
            mRootScreenList.findByKey(key).setDataBundle(savedValues.getBundle(key));
        }
    }

    public void registerListener(ScreenModelCallbacks listener) {
        mListeners.add(listener);
    }

    public Bundle save() {
        Bundle bundle = new Bundle();
        for (Screen page : getCurrentScreenSequence()) {
            bundle.putBundle(page.getKey(), page.getDataBundle());
        }
        return bundle;
    }

    /**
     * Gets the current list of wizard steps, flattening nested (dependent) pages based on the
     * user's choices.
     */
    public List<Screen> getCurrentScreenSequence() {
        ArrayList<Screen> flattened = new ArrayList<Screen>();
        mRootScreenList.flattenCurrentScreenSequence(flattened);
        return flattened;
    }

    public void unregisterListener(ScreenModelCallbacks listener) {
        mListeners.remove(listener);
    }
}
