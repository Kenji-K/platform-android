package com.ushahidi.platform.mobile.app.data.api.model;

import com.ushahidi.platform.mobile.app.BuildConfig;
import com.ushahidi.platform.mobile.app.data.api.BaseApiTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.platform.mobile.app.data.TestHelper.getResource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class FormsTest extends BaseApiTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessfullyDeserializeForms() throws IOException {
        final String formJson = getResource("forms.json");
        final Forms forms = gson.fromJson(formJson, Forms.class);
        assertThat(forms).isNotNull();
        assertThat(forms.getForms()).isNotNull();
        assertThat(forms.getForms().size()).isEqualTo(15);
        assertThat(forms.getForms().get(0).getCreated()).isNotNull();
        assertThat(forms.getForms().get(0).getUpdated()).isNotNull();
        assertThat(forms.getForms().get(0).getDescription())
                .isEqualTo("For community actions and  interactions");
        assertThat(forms.getForms().get(0).getName()).isEqualTo("Noticeboard");
        assertThat(forms.getForms().get(0).getDeploymentId()).isNull();
    }
}
