package com.ushahidi.android.presentation.presenter;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
import com.ushahidi.android.domain.usecase.deployment.DeleteDeploymentUsecase;
import com.ushahidi.android.domain.usecase.form.DeleteFormUsecase;
import com.ushahidi.android.domain.usecase.geojson.DeleteGeoJsonUsecase;
import com.ushahidi.android.domain.usecase.post.DeletePostUsecase;
import com.ushahidi.android.domain.usecase.tag.DeleteTagUsecase;
import com.ushahidi.android.domain.usecase.user.DeleteUserProfileUsecase;

import com.ushahidi.android.presentation.model.mapper.DeploymentModelDataMapper;
import com.ushahidi.android.presentation.presenter.deployment.DeleteDeploymentPresenter;
import com.ushahidi.android.presentation.view.deployment.DeleteDeploymentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import rx.Subscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Tests {@link DeleteDeploymentPresenter}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class DeleteDeploymentPresenterTest {

    private DeleteDeploymentPresenter mDeleteDeploymentPresenter;

    @Mock
    private Context mMockContext;

    @Mock
    private DeleteDeploymentView mMockDeleteDeploymentView;

    @Mock
    private DeleteDeploymentUsecase mMockDeleteDeploymentUsecase;

    @Mock
    private DeleteFormUsecase mMockDeleteFormUsecase;

    @Mock
    private DeleteGeoJsonUsecase mMockDeleteGeoJsonUsecase;

    @Mock
    private DeletePostUsecase mMockDeletePostUsecase;

    @Mock
    private DeleteTagUsecase mMockDeleteTagUsecase;

    @Mock
    private DeleteUserProfileUsecase mMockDeleteUserProfileUsecase;

    @Mock
    private DeploymentModelDataMapper mMockDeploymentDataMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mDeleteDeploymentPresenter = new DeleteDeploymentPresenter(mMockDeleteDeploymentUsecase,
                mMockDeleteFormUsecase, mMockDeleteGeoJsonUsecase, mMockDeletePostUsecase,
                mMockDeleteTagUsecase, mMockDeleteUserProfileUsecase);
        mDeleteDeploymentPresenter.setView(mMockDeleteDeploymentView);
    }

    @Test
    public void testDeploymentDeletion() {
        given(mMockDeleteDeploymentView.getAppContext()).willReturn(mMockContext);
        mDeleteDeploymentPresenter.deleteDeployment(1l);

        verify(mMockDeleteDeploymentUsecase).setDeploymentId(1l);
        verify(mMockDeleteDeploymentUsecase).execute(any(Subscriber.class));
    }
}