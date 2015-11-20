package com.ushahidi.android.presentation.view.ui.adapter;

import com.ushahidi.android.presentation.view.ui.form.wizard.Screen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostFragmentStatePageAdapter extends BaseFragmentStatePageAdapter {

    private int mTotalPage;

    private List<Screen> mFragmentPages;

    private List<String> mStepTitle;

    private Fragment mPrimaryItem;

    public AddPostFragmentStatePageAdapter(FragmentManager fragmentManager,
            List<Screen> fragmentPages, List<String> stepTitle) {
        super(fragmentManager);
        mFragmentPages = fragmentPages;
        mStepTitle = stepTitle;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentPages.get(position).createFragment();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO: be smarter about this
        if (object == mPrimaryItem) {
            // Re-use the current fragment (its position never changes)
            return POSITION_UNCHANGED;
        }

        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mPrimaryItem = (Fragment) object;
    }


    @Override
    public int getCount() {
        return mFragmentPages.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mStepTitle.get(position);
    }
}
