package co.netguru.android.socialslack.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.feature.channels.ChannelsFragment
import co.netguru.android.socialslack.feature.main.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val OFF_SCREEN_PAGE_LIMIT = 2
        const val REQUEST_SORT_CHANNELS = 101
        const val REQUEST_EXTRA = "requestExtra"

        private const val REQUEST_DEFAULT = 0

        fun startActivityWithRequest(context: Context, requestCode: Int) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(MainActivity.REQUEST_EXTRA, requestCode)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

    private val mainPagerAdapter by lazy { MainPagerAdapter(supportFragmentManager) }
    private val tabManager by lazy { TabManager(tabLayout, viewPager, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeToolbar()
        initializePager()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        when (intent.getIntExtra(REQUEST_EXTRA, REQUEST_DEFAULT)) {
            REQUEST_SORT_CHANNELS -> refreshDataOnChannelsFragment()
            else -> throw IllegalStateException("Intent should contains REQUEST_EXTRA")
        }
    }

    private fun refreshDataOnChannelsFragment() {
        supportFragmentManager.fragments.forEach({
            if (it is ChannelsFragment) {
                it.sortData()
            }
        })
    }

    // Suppress RestrictedApi warning because of this bug https://issuetracker.google.com/issues/37130193
    @SuppressLint("RestrictedApi")
    private fun initializeToolbar() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.let {
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDefaultDisplayHomeAsUpEnabled(false)
        }
    }

    private fun initializePager() {
        viewPager.offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
        viewPager.adapter = mainPagerAdapter
        tabManager.init()
    }
}