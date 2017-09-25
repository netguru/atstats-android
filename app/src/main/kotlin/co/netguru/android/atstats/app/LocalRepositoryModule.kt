package co.netguru.android.atstats.app

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class LocalRepositoryModule {

    companion object {
        const val THEME_OPTION_SHARED_PREFERENCES_NAME = "themeOption"
    }

    @Named(THEME_OPTION_SHARED_PREFERENCES_NAME)
    @Singleton
    @Provides
    fun provideThemeOptionSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName +
                THEME_OPTION_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}