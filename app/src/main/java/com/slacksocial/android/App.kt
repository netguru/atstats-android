package com.slacksocial.android

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics ())
    }
}