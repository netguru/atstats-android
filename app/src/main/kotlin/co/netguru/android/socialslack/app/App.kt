package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen

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

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        applicationComponent.getDebugMetricsHelper().init(this)
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
