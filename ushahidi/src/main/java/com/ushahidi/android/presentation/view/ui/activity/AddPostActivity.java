/*
 * Copyright (c) 2015 Ushahidi.
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program in the file LICENSE-AGPL. If not, see
 * https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.view.ui.activity;

import com.addhen.android.raiburari.presentation.di.HasComponent;
import com.cocosw.bottomsheet.BottomSheet;
import com.ushahidi.android.R;
import com.ushahidi.android.data.PrefsFactory;
import com.ushahidi.android.data.api.account.PlatformSession;
import com.ushahidi.android.data.api.account.SessionManager;
import com.ushahidi.android.presentation.di.components.post.AddPostComponent;
import com.ushahidi.android.presentation.di.components.post.DaggerAddPostComponent;
import com.ushahidi.android.presentation.model.FormAttributeModel;
import com.ushahidi.android.presentation.model.FormStageModel;
import com.ushahidi.android.presentation.model.PostModel;
import com.ushahidi.android.presentation.model.TagModel;
import com.ushahidi.android.presentation.presenter.formattribute.ListFormAttributePresenter;
import com.ushahidi.android.presentation.presenter.formstage.ListFormStagePresenter;
import com.ushahidi.android.presentation.presenter.post.AddPostPresenter;
import com.ushahidi.android.presentation.presenter.tags.ListTagPresenter;
import com.ushahidi.android.presentation.util.ConfigurePostModelUtility;
import com.ushahidi.android.presentation.util.PostValueUtility;
import com.ushahidi.android.presentation.util.Utility;
import com.ushahidi.android.presentation.view.formattribute.ListFormAttributeView;
import com.ushahidi.android.presentation.view.formstage.ListFormStageView;
import com.ushahidi.android.presentation.view.post.AddPostView;
import com.ushahidi.android.presentation.view.tags.ListTagsView;
import com.ushahidi.android.presentation.view.ui.adapter.AddPostFragmentStatePageAdapter;
import com.ushahidi.android.presentation.view.ui.form.ScreenFragmentCallbacks;
import com.ushahidi.android.presentation.view.ui.form.ui.widgets.Widget;
import com.ushahidi.android.presentation.view.ui.form.wizard.Screen;
import com.ushahidi.android.presentation.view.ui.form.wizard.ScreenModelCallbacks;
import com.ushahidi.android.presentation.view.ui.form.wizard.model.AbstractScreenModel;
import com.ushahidi.android.presentation.view.ui.form.wizard.model.PostFormWizardModel;
import com.ushahidi.android.presentation.view.ui.form.wizard.model.PostItemModel;
import com.ushahidi.android.presentation.view.ui.fragment.AddPostFragment;
import com.ushahidi.android.presentation.view.ui.widget.ScrollConfigurableViewPager;
import com.ushahidi.android.presentation.view.ui.widget.TitlePageIndicator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Renders {@link AddPostFragment}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AddPostActivity extends BaseAppActivity
        implements HasComponent<AddPostComponent>, ScreenFragmentCallbacks, ScreenModelCallbacks {

    private static final String INTENT_EXTRA_PARAM_FORM_ID
            = "com.ushahidi.android.INTENT_PARAM_FORM_ID";

    private static final String INTENT_EXTRA_PARAM_FORM_TITLE
            = "com.ushahidi.android.INTENT_PARAM_FORM_TITLE";

    private static final String BUNDLE_STATE_PARAM_FORM_ID
            = "com.ushahidi.android.STATE_PARAM_FORM_ID";

    private static final String BUNDLE_STATE_PARAM_FORM_TITLE
            = "com.ushahidi.android.STATE_PARAM_FORM_TITLE";

    private static final String BUNDLE_STATE_PARAM_FORM_STEPS
            = "com.ushahidi.android.STATE_PARAM_FORM_STEPS";

    private static final String BUNDLE_STATE_PARAM_FORM_ATTRIBUTES
            = "com.ushahidi.android.STATE_PARAM_FORM_ATTRIBUTES";

    private static final String FRAG_TAG = "add_post";

    @Bind(R.id.add_post_view_pager)
    ScrollConfigurableViewPager mAddPostViewPager;

    @Bind(R.id.next_post_step_button)
    Button mNextButton;

    @Bind(R.id.previous_post_step_button)
    Button mPrevButton;

    @Inject
    ListTagPresenter mListTagPresenter;

    @Bind(R.id.post_step_title_strip)
    TitlePageIndicator mStepPagerStrip;

    @Inject
    ListFormAttributePresenter mListFormAttributePresenter;

    @Inject
    ListFormStagePresenter mListFormStagePresenter;

    @Inject
    AddPostPresenter mAddPostPresenter;

    @Inject
    PrefsFactory mPrefsFactory;

    private SessionManager<PlatformSession> mSessionManager;

    private AddPostComponent mAddPostComponent;

    private AddPostFragment mAddPostFragment;

    private Long mFormId;

    private String mFormTitle;

    private AddPostFragmentStatePageAdapter mAddPostFragmentStatePageAdapter;

    private AbstractScreenModel mScreenModel;

    private boolean mConsumeScreenSelectedEvent;

    private List<Screen> mCurrentScreenSequence = new ArrayList<>();

    private List<FormStageModel> mFormStages = new ArrayList<>();

    private List<FormAttributeModel> mFormAttributeModels = new ArrayList<>();

    /**
     * Default constructor
     */
    public AddPostActivity() {
        super(R.layout.activity_add_post, 0);
    }

    /**
     * Provides {@link Intent} for launching this activity
     *
     * @param context The calling context
     * @return The intent to launch this activity
     */
    public static Intent getIntent(final Context context, Long formId, String formTitle) {
        Intent intent = new Intent(context, AddPostActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_FORM_ID, formId);
        intent.putExtra(INTENT_EXTRA_PARAM_FORM_TITLE, formTitle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector();
        initialize(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mListFormAttributePresenter.pause();
        mListFormStagePresenter.pause();
        mAddPostPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListFormStagePresenter.destroy();
        mListFormAttributePresenter.destroy();
        mAddPostPresenter.destroy();
        if (mScreenModel != null) {
            mScreenModel.unregisterListener(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(BUNDLE_STATE_PARAM_FORM_ID, mFormId);
        savedInstanceState.putString(BUNDLE_STATE_PARAM_FORM_TITLE, mFormTitle);
        savedInstanceState
                .putParcelableArrayList(BUNDLE_STATE_PARAM_FORM_STEPS, (ArrayList) mFormStages);
        savedInstanceState.putParcelableArrayList(BUNDLE_STATE_PARAM_FORM_ATTRIBUTES,
                (ArrayList) mFormAttributeModels);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void injector() {
        mAddPostComponent = DaggerAddPostComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(getActivityModule())
                .build();
        mSessionManager = getAppComponent().platformSessionManager();
        getComponent().inject(this);
    }

    private void initialize(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFormId = getIntent().getLongExtra(INTENT_EXTRA_PARAM_FORM_ID, 0l);
            mFormTitle = getIntent().getStringExtra(INTENT_EXTRA_PARAM_FORM_TITLE);
            initializeTagsView();
            initializeFormAttributeView();

        } else {
            mFormId = savedInstanceState.getLong(BUNDLE_STATE_PARAM_FORM_ID);
            mFormTitle = savedInstanceState.getString(BUNDLE_STATE_PARAM_FORM_TITLE);
            mFormStages = (ArrayList) savedInstanceState
                    .getParcelableArrayList(BUNDLE_STATE_PARAM_FORM_STEPS);
            mFormAttributeModels = (ArrayList) savedInstanceState
                    .getParcelableArrayList(BUNDLE_STATE_PARAM_FORM_ATTRIBUTES);
        }

        getSupportActionBar().setTitle(mFormTitle);
    }

    private void initView() {
        mStepPagerStrip.setViewPager(mAddPostViewPager);
        mStepPagerStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!isScreenValid()) {
                    showToast(R.string.required_field_error);
                    mAddPostViewPager.enableScroll(false);
                    mAddPostViewPager.setCurrentItem(position);
                } else {
                    mAddPostViewPager.enableScroll(true);
                }
                updateBottomBar();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mAddPostViewPager.getCurrentItem() == mCurrentScreenSequence.size() - 1) {
                    ArrayList<PostItemModel> postItems = new ArrayList<PostItemModel>();
                    boolean isValid = true;
                    for (int i = 0; i < mCurrentScreenSequence.size(); i++) {
                        Screen screen = mCurrentScreenSequence.get(i);
                        for (Widget widget : screen.getWidgets()) {
                            if (!widget.validate()) {
                                isValid = false;
                                break;
                            }

                        }
                        screen.getPostItems(postItems);
                    }
                    Collections.sort(postItems, new Comparator<PostItemModel>() {
                        @Override
                        public int compare(PostItemModel a, PostItemModel b) {
                            return a.getWeight() > b.getWeight() ? +1
                                    : a.getWeight() < b.getWeight() ? -1 : 0;
                        }
                    });
                    if (isValid) {
                        // TODO: Post item to the API
                        // Get form attributes and build post value
                        ConfigurePostModelUtility.Builder configurePostModel
                                = new ConfigurePostModelUtility.Builder(
                                mPrefsFactory.getActiveDeploymentId().get(),
                                "Title", "Content");
                        PostValueUtility.Builder postBuilder = new PostValueUtility.Builder();
                        for (PostItemModel postItemModel : postItems) {
                            FormAttributeModel formAttributeModel = findFormAttribute(
                                    postItemModel.getFieldKey());
                            if (formAttributeModel != null) {
                                // Process point and geometry differently. They should be returned
                                // as object not array
                                if (!formAttributeModel.getType()
                                        .equals(FormAttributeModel.Type.POINT)
                                        || !formAttributeModel.getType()
                                        .equals(FormAttributeModel.Type.GEOMETRY)) {
                                    List<String> values = new ArrayList<String>();
                                    values.add(postItemModel.getDisplayValue());
                                    postBuilder.withArray(postItemModel.getFieldKey(), values);
                                } else if (!formAttributeModel.getType()
                                        .equals(FormAttributeModel.Type.POINT)) {
                                    Map<String, String> data = new HashMap<>();
                                    // The LocationWidget returns in values with a : separator
                                    // Split by that to get the individual values
                                    final String[] location = postItemModel.getDisplayValue()
                                            .split(":");
                                    data.put("location", location[0]);
                                    data.put("lat", location[1]);
                                    data.put("lon", location[2]);
                                    postBuilder.withObject(postItemModel.getFieldKey(), data);
                                    // TODO: Configure geometry
                                }
                            }
                        }
                        configurePostModel.postValue(postBuilder.build());
                        configurePostModel.postFormId(mFormId);
                        configurePostModel.postUserId(mSessionManager.getActiveSession().getId());
                        //TODO: Configure post tags and form stages
                        new BottomSheet.Builder(AddPostActivity.this).sheet(R.menu.menu_publish_to)
                                .listener(((dialog, which) -> {
                                    PostModel postModel;
                                    switch (which) {
                                        case R.id.menu_publish_post_to_editors:
                                            //TODO configure publish to everyone
                                            configurePostModel.postPublishTo("user");
                                            postModel = configurePostModel.build().getPostModel();
                                            mAddPostPresenter.addPost(postModel);
                                            break;
                                        case R.id.menu_publish_post_to_admin:
                                            configurePostModel.postPublishTo("admin");
                                            postModel = configurePostModel.build().getPostModel();
                                            mAddPostPresenter.addPost(postModel);
                                            break;
                                        default:
                                            postModel = configurePostModel.build().getPostModel();
                                            mAddPostPresenter.addPost(postModel);
                                            break;
                                    }
                                }))
                                .show();
                    }
                } else {
                    mAddPostViewPager.setCurrentItem(mAddPostViewPager.getCurrentItem() + 1);
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddPostViewPager.setCurrentItem(mAddPostViewPager.getCurrentItem() - 1);
            }
        });
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mAddPostViewPager.getCurrentItem();
        if (position == mCurrentScreenSequence.size() - 1) {
            mNextButton.setText(R.string.finish);
        } else {
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v,
                    true);
            mNextButton.setTextAppearance(this, v.resourceId);
        }
        if (position <= 0) {
            mPrevButton.setText(R.string.who_can_see);
        } else {
            mPrevButton.setText(R.string.prev);
        }
    }

    private void initializeFormAttributeView() {
        mListFormAttributePresenter.setView(new ListFormAttributeView() {
            @Override
            public void renderFormAttribute(List<FormAttributeModel> formModel) {
                mFormAttributeModels = formModel;
                initListFormStageView();
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showRetry() {

            }

            @Override
            public void hideRetry() {

            }

            @Override
            public void showError(String s) {
                showSnackbar(mAddPostViewPager, s);
            }

            @Override
            public Context getAppContext() {
                return getApplicationContext();
            }
        });
        mListFormAttributePresenter.getFormOnline(mFormId);
    }

    private void initListFormStageView() {
        mListFormStagePresenter.setView(new ListFormStageView() {
            @Override
            public void showError(String message) {

            }

            @Override
            public Context getAppContext() {
                return getApplicationContext();
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showRetry() {

            }

            @Override
            public void hideRetry() {

            }

            @Override
            public void renderFormStage(List<FormStageModel> formModels) {
                setupPageView(formModels);
            }
        });
        mListFormStagePresenter.getFormOnline(mFormId);
    }


    private void addPost(PostModel postModel) {
        mAddPostPresenter.setView(new AddPostView() {
            @Override
            public void onPostSuccessfullyAdded(Long row) {

            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showRetry() {

            }

            @Override
            public void hideRetry() {

            }

            @Override
            public void showError(String message) {
            }

            @Override
            public Context getAppContext() {
                return AddPostActivity.this;
            }
        });
        mAddPostPresenter.addPost(postModel);
    }

    private void initializeTagsView() {
        mListTagPresenter.setView(new ListTagsView() {
            @Override
            public void renderTagList(List<TagModel> tagModels) {
                if (!Utility.isCollectionEmpty(tagModels)) {
                    /*mCategories.setVisibility(View.VISIBLE);
                    for (TagModel tag : tagModels) {
                        Timber.i("RenderTags", "Tag: " + tag.getTag());
                        CheckBox checkBox = new CheckBox(getAppContext());
                        int id = Resources.getSystem()
                                .getIdentifier("btn_check_holo_light", "drawable", "android");
                        checkBox.setButtonDrawable(id);
                        checkBox.setTag(tag._id);
                        checkBox.setText(tag.getTag());
                        checkBox.setTextColor(getResources().getColor(R.color.black_dark));
                        mCategories.addView(checkBox);
                    }*/
                }
            }

            @Override
            public void showLoading() {
                // Do nothing
            }

            @Override
            public void hideLoading() {
                // Do nothing
            }

            @Override
            public void showRetry() {
                // Do nothing
            }

            @Override
            public void hideRetry() {
                // Do nothing
            }

            @Override
            public void showError(String s) {
                // Do nothing
            }

            @Override
            public Context getAppContext() {
                return getApplicationContext();
            }
        });
        mListTagPresenter.loadTags();
    }

    @Override
    public AddPostComponent getComponent() {
        return mAddPostComponent;
    }

    @Override
    public void onScreenDataChanged(Screen page) {
        if (isScreenValid()) {
            mAddPostViewPager.enableScroll(true);
        } else {
            mAddPostViewPager.enableScroll(false);
        }
    }

    @Override
    public Screen onGetScreen(String key) {
        if (mScreenModel != null) {
            return mScreenModel.findByKey(key);
        }
        return null;
    }

    private boolean isScreenValid() {
        Screen page = mCurrentScreenSequence.get(mAddPostViewPager.getCurrentItem());
        if (page.isRequired() && !page.isCompleted()) {
            return false;
        }
        return true;
    }

    private void setupPageView(List<FormStageModel> result) {
        mFormStages = result;
        mScreenModel = new PostFormWizardModel(this, mFormStages, mFormAttributeModels);
        mScreenModel.registerListener(this);
        List<String> titles = new ArrayList<>();
        for (FormStageModel stage : mFormStages) {
            titles.add(stage.getLabel());
        }
        mCurrentScreenSequence = mScreenModel.getCurrentScreenSequence();
        mAddPostFragmentStatePageAdapter = new AddPostFragmentStatePageAdapter(
                getSupportFragmentManager(), mCurrentScreenSequence, titles);
        mAddPostViewPager.setAdapter(mAddPostFragmentStatePageAdapter);
        initView();
    }

    @Nullable
    private FormAttributeModel findFormAttribute(@NonNull String formAttributeKey) {
        if (Utility.isCollectionEmpty(mFormAttributeModels)) {
            FormAttributeModel foundFormAttributeModel = null;
            for (FormAttributeModel formAttributeModel : mFormAttributeModels) {
                if (formAttributeModel.getKey().equals(formAttributeKey)) {
                    foundFormAttributeModel = formAttributeModel;
                    break;
                }
            }
            return foundFormAttributeModel;
        }
        return null;
    }
}
