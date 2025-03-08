package com.vedatakcan.reposearchapp.di

import android.content.Context
import com.vedatakcan.reposearchapp.repository.ThemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ThemeModule {

    @Provides
    @Singleton
    fun provideThemeRepository(@ApplicationContext context: Context): ThemeRepository {
        val sharedPreferences =
            context.getSharedPreferences("theme_preferences", Context.MODE_PRIVATE)
        return ThemeRepository(sharedPreferences)
    }
}
