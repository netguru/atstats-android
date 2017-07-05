package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context

class App : Application() {

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
