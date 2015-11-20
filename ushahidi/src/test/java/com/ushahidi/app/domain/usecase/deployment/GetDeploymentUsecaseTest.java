package com.ushahidi.platform.mobile.app.domain.usecase.deployment;

import com.addhen.android.raiburari.domain.executor.PostExecutionThread;
import com.addhen.android.raiburari.domain.executor.ThreadExecutor;
import com.ushahidi.platform.mobile.app.BuildConfig;
import com.ushahidi.platform.mobile.app.DefaultConfig;
import com.ushahidi.platform.mobile.app.domain.repository.DeploymentRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Tests {@link GetDeploymentUsecase}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class GetDeploymentUsecaseTest {

    private static final Long DUMMY_DEPLOYMENT_ID = 1l;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DeploymentRepository mockDeploymentRepository;

    private GetDeploymentUsecase mGetDeploymentUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mGetDeploymentUsecase = new GetDeploymentUsecase(mockDeploymentRepository, mockThreadExecutor, mockPostExecutionThread);
        mGetDeploymentUsecase.setDeploymentId(DUMMY_DEPLOYMENT_ID);
    }

    @Test
    public void shouldSuccessfullyDeleteDeployment() {
        mGetDeploymentUsecase.buildUseCaseObservable();
        verify(mockDeploymentRepository).getEntity(DUMMY_DEPLOYMENT_ID);

        verifyNoMoreInteractions(mockDeploymentRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }
}
