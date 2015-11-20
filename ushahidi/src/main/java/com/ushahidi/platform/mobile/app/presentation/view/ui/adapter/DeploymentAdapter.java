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

package com.ushahidi.platform.mobile.app.presentation.view.ui.adapter;

import com.addhen.android.raiburari.presentation.ui.adapter.BaseRecyclerViewAdapter;
import com.ushahidi.platform.mobile.app.R;
import com.ushahidi.platform.mobile.app.presentation.model.DeploymentModel;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for deployment listing recyclerview
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentAdapter extends BaseRecyclerViewAdapter<DeploymentModel> {

    private SparseBooleanArray mSelectedItems;

    /**
     * Default constructor
     */
    public DeploymentAdapter() {
        mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((Widgets) viewHolder).title.setText(getItem(position).getTitle());
        ((Widgets) viewHolder).url.setText(getItem(position).getUrl());
        ((Widgets) viewHolder).listCheckBox.setChecked(mSelectedItems.get(position, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new Widgets(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_deployment_item, parent, false));
    }

    @Override
    public int getAdapterItemCount() {
        return getItems().size();
    }

    /**
     * Toggles an item in the adapter as selected or de-selected
     *
     * @param position The index of the item to be toggled
     */
    public void toggleSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    /**
     * Count of the selected item
     *
     * @return The selected item size
     */
    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    /**
     * Clear all selections
     */
    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * Gets all selected items
     *
     * @return The list of selected items
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

    /**
     * View holder
     */
    public class Widgets extends RecyclerView.ViewHolder {

        TextView title;

        TextView url;

        CheckedTextView listCheckBox;

        /**
         * Default constructor
         *
         * @param convertView The view
         */
        public Widgets(View convertView) {
            super(convertView);
            title = (TextView) convertView.findViewById(R.id.deployment_title);
            url = (TextView) convertView.findViewById(R.id.deployment_description);

            listCheckBox = (CheckedTextView) convertView
                    .findViewById(R.id.deployment_selected);
        }

    }
}
