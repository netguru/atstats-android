package co.netguru.android.socialslack.feature.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.customTheme.CustomThemeActivity
import co.netguru.android.socialslack.common.extensions.getAttributeColor
import co.netguru.android.socialslack.common.extensions.getAttributeDrawable
import co.netguru.android.socialslack.feature.search.adapter.SearchPagerAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : CustomThemeActivity() {

    companion object {
        private const val EMPTY_STRING = ""
    }

    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initializeToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        initializeSearchView(menu)
        initializeViewPager()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    private fun initializeToolbar() {
        toolbar.setBackgroundResource(getAttributeDrawable(R.attr.searchToolbarBackground))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeSearchView(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.actionSearch).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
    }

    private fun initializeViewPager() {
        searchTabLayout.setBackgroundColor(getAttributeColor(R.attr.colorPrimary))
        searchTabLayout.setSelectedTabIndicatorColor(getAttributeColor(R.attr.colorTabIndicator))
        searchTabLayout.setupWithViewPager(searchViewPager)
        searchTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                clearSearchView()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // no-op
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                clearSearchView()
            }

        })
        searchViewPager.adapter = SearchPagerAdapter(supportFragmentManager)
    }

    private fun clearSearchView() {
        searchView.setQuery(EMPTY_STRING, false)
        searchView.clearFocus()
    }
}