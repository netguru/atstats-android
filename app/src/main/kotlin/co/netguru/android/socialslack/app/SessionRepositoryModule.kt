package co.netguru.android.socialslack.app

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class SessionRepositoryModule {

    companion object {
        const val TOKEN_SHARED_PREFERENCES_NAME = "token"
        const val USER_SHARED_PREFERENCES_NAME = "user"
    }

    @Named(TOKEN_SHARED_PREFERENCES_NAME)
    @Singleton
    @Provides
    fun provideTokenSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName +
                TOKEN_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Named(USER_SHARED_PREFERENCES_NAME)
    @Singleton
    @Provides
    fun provideUserSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName +
                USER_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}