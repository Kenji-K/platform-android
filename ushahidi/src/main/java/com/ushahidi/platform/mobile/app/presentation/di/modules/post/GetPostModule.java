package com.ushahidi.platform.mobile.app.presentation.di.modules.post;

import com.addhen.android.raiburari.presentation.di.qualifier.ActivityScope;
import com.ushahidi.platform.mobile.app.domain.usecase.post.GetPostUsecase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Provides injectable modules scoped with {@link ActivityScope} to detail post related classes
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@Module
public class GetPostModule {

    /**
     * Provides {@link GetPostUsecase} object annotated with the name "postList"
     *
     * @param getPostUsecase Get post use case
     * @return The get post use case
     */
    @Provides
    @ActivityScope
    @Named("postGet")
    GetPostUsecase provideListPostUseCase(GetPostUsecase getPostUsecase) {
        return getPostUsecase;
    }
}
