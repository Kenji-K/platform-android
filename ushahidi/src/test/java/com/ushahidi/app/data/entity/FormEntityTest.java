package com.ushahidi.platform.mobile.app.data.entity;

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
public class FormEntityTest {

    @Test
    public void shouldSetFormEntityTest() {
        FormEntity formEntity = TestEntityFixtures.getFormEntity();
        assertThat(formEntity).isNotNull();
        assertThat(formEntity).isInstanceOf(FormEntity.class);
        assertThat(formEntity.getName()).isNotNull();
        assertThat(formEntity.getName()).isEqualTo(TestEntityFixtures.getFormEntity().getName());
        assertThat(formEntity.getDeploymentId()).isNotNull();
        assertThat(formEntity.getDeploymentId()).isEqualTo(
                TestEntityFixtures.getFormEntity().getDeploymentId());
        assertThat(formEntity.getDescription()).isNotNull();
        assertThat(formEntity.getDescription())
                .isEqualTo(TestEntityFixtures.getFormEntity().getDescription());
        assertThat(formEntity.getCreated()).isNotNull();
        assertThat(formEntity.getCreated())
                .isEqualTo(TestEntityFixtures.getFormEntity().getCreated());
        assertThat(formEntity.getUpdated()).isNotNull();
        assertThat(formEntity.getUpdated())
                .isEqualTo(TestEntityFixtures.getFormEntity().getUpdated());
        assertThat(formEntity.isDisabled()).isTrue();
    }
}
