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


import java.util.ArrayList;

/**
 * Represents a list of wizard screens.
 */
public class ScreenList extends ArrayList<Screen> implements ScreenTreeNode {

    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = -2829971774651385639L;

    public ScreenList(Screen... screens) {
        for (Screen screen : screens) {
            add(screen);
        }
    }

    @Override
    public Screen findByKey(String key) {
        for (Screen childScreen : this) {
            Screen found = childScreen.findByKey(key);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Override
    public void flattenCurrentScreenSequence(ArrayList<Screen> dest) {
        for (Screen childScreen : this) {
            childScreen.flattenCurrentScreenSequence(dest);
        }
    }
}
