package co.netguru.android.socialslack.app

import android.content.Context
import android.os.Handler
import javax.inject.Inject
import javax.inject.Singleton
import com.github.moduth.blockcanary.BlockCanaryContext
import com.github.moduth.blockcanary.BlockCanary
import timber.log.Timber
import com.squareup.leakcanary.LeakCanary
import android.os.StrictMode
import com.nshmura.strictmodenotifier.StrictModeNotifier
import com.facebook.stetho.Stetho
import com.frogermcs.androiddevmetrics.AndroidDevMetrics

/**
 * Helper class that initializes a set of debugging tools
 * for the debug build type and register crash manager for release type.
 * <p>
 * Debug type tools:
 * <ul>
 * <li> AndroidDevMetrics
 * <li> Stetho
 * <li> StrictMode
 * <li> LeakCanary
 * <li> Timber
 * </ul>
 * Release type tools:
 * <ul>
 * <li> CrashManager
 * </ul>
 * <p>
 */
@Singleton
class DebugMetricsHelper @Inject constructor() {

    internal fun init(context: Context) {
        // AndroidDevMetrics
        AndroidDevMetrics.initWith(context)

        // Stetho
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                .build())

        // StrictMode
        StrictModeNotifier.install(context)
        Handler().post({
            val threadPolicy = StrictMode.ThreadPolicy.Builder().detectAll()
                    .permitDiskReads()
                    .permitDiskWrites()
                    .penaltyLog() // Must!
                    .build()
            StrictMode.setThreadPolicy(threadPolicy)

            val vmPolicy = StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog() // Must!
                    .build()
            StrictMode.setVmPolicy(vmPolicy)
        })

        // LeakCanary
        LeakCanary.install(context.applicationContext as App)

        //Timber
        Timber.plant(Timber.DebugTree())

        //BlockCanary
        BlockCanary.install(context, BlockCanaryContext()).start()
    }
}