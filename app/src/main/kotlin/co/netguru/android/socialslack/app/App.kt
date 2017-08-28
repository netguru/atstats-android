package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context
import co.netguru.android.socialslack.BuildConfig
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class App : Application() {

    companion object Factory {

        internal fun getApplicationComponent(context: Context): ApplicationComponent =
                (context.applicationContext as App).applicationComponent

        internal fun getUserComponent(context: Context): UserComponent =
                (context.applicationContext as App).getUserComponent()

        internal fun releaseUserComponent(context: Context) {
            (context.applicationContext as App).userComponent = null
        }

        internal fun initUserComponent(context: Context, userId: String) {
            (context.applicationContext as App).setUpUserComponent(userId)
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

    internal fun setUpUserComponent(userId: String) {
        this.userComponent = applicationComponent
                .userComponentBuilder()
                .userLocalRepositoryModule(UserLocalRepositoryModule(userId))
                .build()
    }

    internal fun getUserComponent(): UserComponent {
        if (userComponent == null) {
            applicationComponent.userComponentRestorer().restoreUserComponent()
        }

        return userComponent!!
    }
}
