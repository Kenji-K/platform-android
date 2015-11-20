package com.ushahidi.platform.mobile.app.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ushahidi.platform.mobile.app.BuildConfig;
import com.ushahidi.platform.mobile.app.DefaultConfig;
import com.ushahidi.platform.mobile.app.data.api.AllowedPrivilegesDeserializer;
import com.ushahidi.platform.mobile.app.data.api.Date;
import com.ushahidi.platform.mobile.app.data.api.DateDeserializer;
import com.ushahidi.platform.mobile.app.data.api.PostCompletedStagesDeserializer;
import com.ushahidi.platform.mobile.app.data.api.PostPublishToDeserializer;
import com.ushahidi.platform.mobile.app.data.api.PostValueDeserializer;
import com.ushahidi.platform.mobile.app.data.entity.AllowedPrivilegesEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostCompletedStagesEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostPublishedToEntity;
import com.ushahidi.platform.mobile.app.data.entity.PostValueEntity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public abstract class BaseTestCase {

    public Gson gson;

    @Before
    public void setUp() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        builder.registerTypeAdapter(PostValueEntity.class, new PostValueDeserializer());
        builder.registerTypeAdapter(AllowedPrivilegesEntity.class,
                new AllowedPrivilegesDeserializer());
        builder.registerTypeAdapter(PostCompletedStagesEntity.class,
                new PostCompletedStagesDeserializer());
        builder.registerTypeAdapter(PostPublishedToEntity.class, new PostPublishToDeserializer());
        gson = builder.create();

    }

    /**
     * Resets a Singleton class. Uses Reflection to find a private field called sInstance then
     * nullifies the field.
     *
     * @param clazz The class to reset.
     */
    protected void clearSingleton(Class clazz) {
        Field instance;
        try {
            instance = clazz.getDeclaredField("sInstance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
