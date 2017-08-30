package co.netguru.android.socialslack.feature.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

    private val searchPagerAdapter by lazy { SearchPagerAdapter(supportFragmentManager) }
    private lateinit var searchView: SearchView

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
        when (intent.action) {
            Intent.ACTION_SEARCH -> refreshSearchFragmentData(intent.getStringExtra(SearchManager.QUERY))
            else -> throw IllegalStateException("There is no action for ${intent.action}")
        }
    }

    private fun refreshSearchFragmentData(query: String) {
        searchPagerAdapter.refreshFragment(searchTabLayout.selectedTabPosition, query)
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

        val clearButton = searchView.findViewById<View>(R.id.search_close_btn)
        clearButton.setOnClickListener { clearSearchView() }
    }

    private fun initializeViewPager() {
        searchTabLayout.setBackgroundColor(getAttributeColor(R.attr.colorPrimary))
        searchTabLayout.setSelectedTabIndicatorColor(getAttributeColor(R.attr.colorTabIndicator))
        searchTabLayout.setupWithViewPager(searchViewPager)
        searchTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                // no-op
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                clearSearchView()
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                // no-op
            }
        })
        searchViewPager.adapter = searchPagerAdapter
    }

    private fun clearSearchView() {
        searchView.setQuery(EMPTY_STRING, false)
        searchPagerAdapter.refreshFragment(searchTabLayout.selectedTabPosition, EMPTY_STRING)
        searchView.isIconified = true
        searchView.clearFocus()
    }
}