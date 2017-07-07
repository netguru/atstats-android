package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }

    private val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .build()
    }

    companion object Factory {
        fun getApplicationComponent(context: Context): ApplicationComponent =
                (context.applicationContext as App).applicationComponent
    }
}
