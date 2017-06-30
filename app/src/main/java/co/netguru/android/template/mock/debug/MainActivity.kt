package co.netguru.android.template.mock.debug

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.netguru.android.template.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
