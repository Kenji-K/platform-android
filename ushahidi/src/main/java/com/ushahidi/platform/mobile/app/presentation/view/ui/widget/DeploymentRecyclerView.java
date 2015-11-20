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

package com.ushahidi.platform.mobile.app.presentation.view.ui.widget;

import com.addhen.android.raiburari.presentation.ui.widget.BloatedRecyclerView;
import com.ushahidi.platform.mobile.app.R;
import com.ushahidi.platform.mobile.app.presentation.model.DeploymentModel;
import com.ushahidi.platform.mobile.app.presentation.presenter.deployment.DeleteDeploymentPresenter;
import com.ushahidi.platform.mobile.app.presentation.view.ui.adapter.DeploymentAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Custom {@link RecyclerView} to handle CAB events for deployments list
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentRecyclerView extends BloatedRecyclerView
        implements RecyclerView.OnItemTouchListener {

    /** Indicates an invalid positon */
    public static final int INVALID_POSITION = -1;

    /** List of items pending to to be deleted **/
    public List<PendingDeletedDeployment> mPendingDeletedDeployments;

    private Activity mActivity;

    private boolean mSelectionMode = false;

    private int mStartPosition;

    private ActionMode mActionMode;

    private DeploymentAdapter mDeploymentAdapter;

    private boolean mIsPermanentlyDeleted = true;

    private DeleteDeploymentPresenter mDeleteDeploymentPresenter;

    private FloatingActionButton mFloatingActionButton;

    public DeploymentRecyclerView(Context context) {
        this(context, null, 0);
    }

    public DeploymentRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeploymentRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mActivity = (Activity) context;
        mPendingDeletedDeployments = new ArrayList<>();
        mDeploymentAdapter = (DeploymentAdapter) getAdapter();
        recyclerView.addOnItemTouchListener(this);
    }

    private int pointToPosition(MotionEvent motionEvent) {

        Rect rect = new Rect();
        View downView = null;

        int childCount = recyclerView.getChildCount();
        int[] listViewCoords = new int[2];
        getLocationOnScreen(listViewCoords);
        int x = (int) motionEvent.getRawX() - listViewCoords[0];
        int y = (int) motionEvent.getRawY() - listViewCoords[1];
        View child;
        for (int i = 0; i < childCount; i++) {
            child = recyclerView.getChildAt(i);
            child.getHitRect(rect);
            if (rect.contains(x, y)) {
                downView = child;
                break;
            }
        }

        if (downView != null) {
            return recyclerView.getChildAdapterPosition(downView);
        }
        return INVALID_POSITION;
    }

    public void setItemChecked(int position) {
        mDeploymentAdapter = (DeploymentAdapter) getAdapter();
        mDeploymentAdapter.toggleSelection(position);

        int checkedCount = mDeploymentAdapter.getSelectedItemCount();

        if (checkedCount == 0) {
            if (mActionMode != null) {
                mActionMode.finish();
            }
            return;
        }
        if (mActionMode == null) {
            mActionMode = mActivity.startActionMode(new ActionBarModeCallback());
        }

        if (mDeploymentAdapter != null) {
            mPendingDeletedDeployments.add(new PendingDeletedDeployment(position,
                    mDeploymentAdapter.getItem(position)));

        }

        // Set the CAB title with the number of selected items
        mActionMode.setTitle(mActivity.getString(R.string.selected, checkedCount));

    }

    public void initAdapter(DeploymentAdapter deploymentAdapter) {
        mDeploymentAdapter = deploymentAdapter;
    }

    /**
     * Clear all checked items in the list and the selected {@link DeploymentModel}
     */
    private void clearItems() {
        mDeploymentAdapter.clearSelections();
        if (mPendingDeletedDeployments != null) {
            mPendingDeletedDeployments.clear();
        }
    }

    public void setDeleteDeploymentPresenter(DeleteDeploymentPresenter deleteDeploymentPresenter) {
        mDeleteDeploymentPresenter = deleteDeploymentPresenter;
    }

    private void setItemsForDeletion() {

        for (PendingDeletedDeployment pendingDeletedDeployment : mPendingDeletedDeployments) {
            mDeploymentAdapter.removeItem(pendingDeletedDeployment.deploymentModel);
        }

        deleteItems();
    }

    public void setFloatingActionButton(FloatingActionButton floatingActionButton) {
        mFloatingActionButton = floatingActionButton;
    }

    public void deleteItems() {
        //Sort in ascending order for restoring deleted items
        Comparator cmp = Collections.reverseOrder();
        Collections.sort(mPendingDeletedDeployments, cmp);
        Snackbar snackbar = Snackbar.make(mFloatingActionButton, mActivity
                        .getString(R.string.items_deleted, mPendingDeletedDeployments.size()),
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, e -> {
            mIsPermanentlyDeleted = false;
            // Restore items
            for (DeploymentRecyclerView.PendingDeletedDeployment pendingDeletedDeployment
                    : mPendingDeletedDeployments) {
                mDeploymentAdapter.addItem(pendingDeletedDeployment.deploymentModel,
                        pendingDeletedDeployment.getPosition());
            }
            clearItems();
        });
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(mActivity.getResources().getColor(R.color.orange));
        snackbar.show();
        // Handler to time the dismissal of the snackbar so users can
        // undo soft deletion or hard delete deployments
        // Hack: to complement Snackbar's limitation
        // See; http://stackoverflow.com/questions/30639470/snackbar-in-support-library-doesnt-include-ondismisslistener
        new Handler(mActivity.getMainLooper()).postDelayed(() -> {
            if (mIsPermanentlyDeleted) {
                if (mPendingDeletedDeployments.size() > 0) {
                    for (PendingDeletedDeployment pendingDeletedDeployment : mPendingDeletedDeployments) {
                        mDeleteDeploymentPresenter
                                .deleteDeployment(
                                        pendingDeletedDeployment.deploymentModel._id);
                    }
                    clearItems();
                }
            }
        }, 3500);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent ev) {
        final int action = ev.getActionMasked();
        final int x = (int) ev.getRawX();

        // Get the right most part of the item in the list
        // This will enable us to have a bigger touch area for the checkbox
        if (action == MotionEvent.ACTION_DOWN && x < getWidth() / 7) {
            mSelectionMode = true;
            mStartPosition = pointToPosition(ev);
        }

        // Resume regular touch event if it's not in the selection mode.
        // The area of the screen being touched is not where the checkbox is.
        if (!mSelectionMode) {
            return super.onTouchEvent(ev);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointToPosition(ev) != mStartPosition) {
                    mSelectionMode = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            default:
                mSelectionMode = false;
                int mItemPosition = pointToPosition(ev);
                if (mStartPosition != INVALID_POSITION) {
                    setItemChecked(mItemPosition);
                    return true;
                }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent ev) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public static class PendingDeletedDeployment implements Comparable<PendingDeletedDeployment> {

        /** The deployment model to be deleted */
        public DeploymentModel deploymentModel;

        private int mPosition;

        public PendingDeletedDeployment(int position, DeploymentModel deploymentModel) {
            mPosition = position;
            this.deploymentModel = deploymentModel;
        }

        @Override
        public int compareTo(PendingDeletedDeployment other) {
            // Sort by descending position
            return other.mPosition - mPosition;
        }

        public int getPosition() {
            return mPosition;
        }
    }

    class ActionBarModeCallback implements ActionMode.Callback {

        boolean isDeleted = false;

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater()
                    .inflate(R.menu.list_deployment_contextual_actionbar_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            if (menuItem.getItemId() == R.id.list_deployment_delete) {
                setItemsForDeletion();
                isDeleted = true;
            }

            if (mActionMode != null) {
                mActionMode.finish();
            }
            return isDeleted;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            if (!isDeleted) {
                clearItems();
            }
            mActionMode = null;
        }
    }
}
