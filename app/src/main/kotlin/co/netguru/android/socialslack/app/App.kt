package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context

import co.netguru.android.socialslack.BuildConfig
import timber.log.Timber

import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


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

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }

    companion object Factory {
        internal fun getApplicationComponent(context: Context): ApplicationComponent =
                (context.applicationContext as App).applicationComponent
    }
}
