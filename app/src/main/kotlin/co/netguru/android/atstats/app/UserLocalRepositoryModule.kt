package co.netguru.android.atstats.app

import android.content.Context
import android.content.SharedPreferences
import co.netguru.android.atstats.app.scope.UserScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UserLocalRepositoryModule(private val userId: String) {

    companion object {
        const val FILTER_OPTION_SHARED_PREFERENCES_NAME = "filterOption"
    }

    @Named(FILTER_OPTION_SHARED_PREFERENCES_NAME)
    @UserScope
    @Provides
    fun provideFilterOptionSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName + userId +
                FILTER_OPTION_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}