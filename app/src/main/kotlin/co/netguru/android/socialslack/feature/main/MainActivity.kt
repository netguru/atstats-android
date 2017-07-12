package co.netguru.android.socialslack.feature.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.feature.main.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val OFF_SCREEN_PAGE_LIMIT = 2
    }

    private val mainPagerAdapter by lazy { MainPagerAdapter(supportFragmentManager) }
    private val tabManager by lazy { TabManager(tabLayout, viewPager, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeToolbar()
        initializePager()
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