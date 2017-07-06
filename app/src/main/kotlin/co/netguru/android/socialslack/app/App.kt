package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context
import co.netguru.android.socialslack.BuildConfig
import timber.log.Timber

class App : Application() {

    private val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    init {
        //TODO 05.07.2017 Move to DebugMetricsHelper class
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object Factory {
        fun getApplicationComponent(context: Context): ApplicationComponent =
                (context.applicationContext as App).applicationComponent
    }
}
