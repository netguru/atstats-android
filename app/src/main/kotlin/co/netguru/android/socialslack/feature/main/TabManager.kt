package co.netguru.android.socialslack.feature.main

import android.content.Context
import android.graphics.PorterDuff
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import co.netguru.android.socialslack.Constants
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.feature.main.adapter.TabItemType
import kotlinx.android.synthetic.main.tab_custom_view.view.*

class TabManager(val tabLayout: TabLayout, val viewPager: ViewPager, val context: Context) {

    private var currentTabSelected = 0
    private val highlightColor by lazy { context.getColor(R.color.primary) }

    companion object {
        const val TAB_LAYOUT_TEXT_SIZE = 14f
        const val TAB_LAYOUT_TEXT_SIZE_SELECTED = 16f
    }

    fun init() {
        tabLayout.setupWithViewPager(viewPager)
        TabItemType.values().forEach { item ->
            val tab = tabLayout.getTabAt(item.position)
            tab?.let {
                val customView = LayoutInflater.from(context).inflate(R.layout.tab_custom_view, null, false)
                customView.tabText.text = context.getString(TabItemType.getTabItemByPosition(tab.position).title)
                customView.tabIcon.setImageResource(item.icon)
                tab.customView = customView

                if (item.position == currentTabSelected) {
                    selectTab(tab)
                }
            }
        }
        tabLayout.addOnTabSelectedListener(createTabListener())
    }

    private fun selectTab(tab: TabLayout.Tab) {
        currentTabSelected = tab.position
        if (tab.position != Constants.UNDEFINED) {
            deselectPreviousTab(currentTabSelected)
        }
        tab.customView?.tabIcon?.setColorFilter(highlightColor, PorterDuff.Mode.SRC_IN)
        tab.customView?.tabText?.setTextColor(highlightColor)
        tab.customView?.tabText?.textSize = TAB_LAYOUT_TEXT_SIZE_SELECTED
        setToolbarForTab(currentTabSelected)
    }

    private fun deselectPreviousTab(currentSelectedTab: Int) {
        (0..tabLayout.tabCount)
                .filter { currentSelectedTab != it }
                .forEach { tabLayout.getTabAt(it)?.deselectTab() }
    }

    private fun TabLayout.Tab.deselectTab() {
        this.customView?.tabIcon?.clearColorFilter()
        this.customView?.tabText?.setTextColor(ContextCompat.getColor(context, R.color.grey))
        this.customView?.tabText?.textSize = TAB_LAYOUT_TEXT_SIZE
    }

    // TODO 07.07.2017 set the toolbar according to the selected tab
    private fun setToolbarForTab(currentSelectedTab: Int) {

    }

    private fun createTabListener(): TabLayout.OnTabSelectedListener {
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