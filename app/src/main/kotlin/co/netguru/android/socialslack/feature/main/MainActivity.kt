package co.netguru.android.socialslack.feature.main

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import co.netguru.android.socialslack.Constants
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.feature.main.adapter.MainPagerAdapter
import co.netguru.android.socialslack.feature.main.adapter.TabItemType
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val OFF_SCREEN_PAGE_LIMIT = 2;
    }

    private val mainPagerAdapter by lazy { MainPagerAdapter(supportFragmentManager) }
    private val highlightColor by lazy { getColor(R.color.primary) }

    private var currentTabSelected = 0

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
        tabLayout.setupWithViewPager(viewPager)

        TabItemType.values().forEach { item ->
            var tab = tabLayout.getTabAt(item.position)
            tab?.let {
                tab.setIcon(item.icon)
                tab.text = getString(TabItemType.getTabItemByPosition(tab.position).title)
                if (item.position == 0) {
                    selectTab(tab)
                }
            }
        }
        tabLayout.addOnTabSelectedListener(createTabListener())
    }

    private fun selectTab(tab: TabLayout.Tab) {
        currentTabSelected = tab.position
        if (tab.position != Constants.UNDEFINED) {
            unselectedPreviousTab(currentTabSelected)
        }
        tab.icon?.setColorFilter(highlightColor, PorterDuff.Mode.SRC_IN)
        setToolbarForTab(currentTabSelected)
    }

    private fun unselectedPreviousTab(currentSelectedTab: Int) {
        (0..tabLayout.tabCount)
                .filter { currentSelectedTab != it }
                .forEach { tabLayout.getTabAt(it)?.icon?.clearColorFilter()}
    }

    // TODO 07.07.2017 set the toolbar according to the selected tab
    private fun setToolbarForTab(currentSelectedTab: Int) {

    }

    private fun createTabListener () : TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.let { selectTab(tab) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // No-op
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { selectTab(tab) }
            }

        }
    }

}