package co.netguru.android.socialslack.app

import android.content.Context
import dagger.Module
import android.content.SharedPreferences
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class LocalRepositoryModule {

    companion object {
        const val TOKEN_SHARED_PREFERENCES_NAME = "token"
        const val FILTER_OPTION_SHARED_PREFERENCES_NAME = "filterOption"
    }

    @Named(TOKEN_SHARED_PREFERENCES_NAME)
    @Singleton
    @Provides
    fun provideTokenSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName +
                TOKEN_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Named(FILTER_OPTION_SHARED_PREFERENCES_NAME)
    @Singleton
    @Provides
    fun provideFilterOptionSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName +
                FILTER_OPTION_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}