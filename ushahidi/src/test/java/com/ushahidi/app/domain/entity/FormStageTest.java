package com.ushahidi.platform.mobile.app.domain.entity;

import com.ushahidi.platform.mobile.app.BuildConfig;
import com.ushahidi.platform.mobile.app.DefaultConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class FormStageTest {

    @Test
    public void shouldSetFormStage() {
        FormStage formStage = TestFixtures.getFormStage();
        assertThat(formStage).isNotNull();
        assertThat(formStage.getFormId()).isEqualTo(2l);
        assertThat(formStage.getLabel()).isEqualTo("Test varchar");
        assertThat(formStage.getRequired()).isTrue();
        assertThat(formStage.getPriority()).isEqualTo(1);
    }
}
