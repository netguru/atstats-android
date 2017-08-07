package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context

import co.netguru.android.socialslack.BuildConfig
import timber.log.Timber

import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class App : Application() {

    companion object Factory {

        //TODO 07.08.2017 For now userId is mocked because we don't get current user profile from server
        //TODO 07.08.2017 UserComponentRestorer should be implemented while implementing ProfileView
        private const val MOCKED_USER_ID = "user"

        internal fun getApplicationComponent(context: Context): ApplicationComponent =
                (context.applicationContext as App).applicationComponent

        internal fun getUserComponent(context: Context): UserComponent =
                (context.applicationContext as App).getUserComponent()

        //TODO 07.08.2017 Should be called when user logout
        internal fun releaseUserComponent(context: Context) {
            (context.applicationContext as App).userComponent = null
        }
    }

    private val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    private var userComponent: UserComponent? = null

    init {
        //TODO 05.07.2017 Move to DebugMetricsHelper class
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }

    internal fun getUserComponent(): UserComponent {
        if (userComponent == null) {
            this.userComponent = applicationComponent
                    .userComponentBuilder()
                    .localRepositoryModule(LocalRepositoryModule(MOCKED_USER_ID))
                    .build()
        }

        return userComponent!!
    }
}
