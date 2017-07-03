package co.netguru.android.template.mock.debug

import android.app.Activity
import android.os.Bundle
import co.netguru.android.template.R

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
