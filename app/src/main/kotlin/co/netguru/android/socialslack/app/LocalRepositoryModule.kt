package co.netguru.android.socialslack.app

import android.content.Context
import dagger.Module
import android.content.SharedPreferences
import co.netguru.android.socialslack.app.scope.UserScope
import dagger.Provides
import javax.inject.Named

@Module
class LocalRepositoryModule(private val userId: String) {

    companion object {
        const val FILTER_OPTION_SHARED_PREFERENCES_NAME = "filterOption"
        const val THEME_OPTION_SHARED_PREFERENCES_NAME = "themeOption"
    }

    @Named(FILTER_OPTION_SHARED_PREFERENCES_NAME)
    @UserScope
    @Provides
    fun provideFilterOptionSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName + userId +
                FILTER_OPTION_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Named(THEME_OPTION_SHARED_PREFERENCES_NAME)
    @UserScope
    @Provides
    fun provideThemeOptionSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName + userId +
        THEME_OPTION_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}