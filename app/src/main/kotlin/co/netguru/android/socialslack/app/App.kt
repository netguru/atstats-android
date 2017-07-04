package co.netguru.android.socialslack.app

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        fun getApplicationComponent(context : Context) : ApplicationComponent {
            val app = context.applicationContext as App
            return app.applicationComponent
        }
    }

    private lateinit var applicationComponent: ApplicationComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        applicationComponent = DaggerApplicationComponent
                .builder()
                .build()
    }
}
