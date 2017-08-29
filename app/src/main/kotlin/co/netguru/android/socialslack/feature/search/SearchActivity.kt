package co.netguru.android.socialslack.feature.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.customTheme.CustomThemeActivity
import co.netguru.android.socialslack.common.extensions.getAttributeColor
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : CustomThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initializeToolbar()

        val fragment = supportFragmentManager.findFragmentById(R.id.searchFragmentContainer)
                ?: SearchFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .replace(R.id.searchFragmentContainer, fragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        initializeSearchView(menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initializeToolbar() {
        toolbar.setBackgroundColor(this.getAttributeColor(R.attr.colorPrimary))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeSearchView(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.actionSearch).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)

        // Workaround for removing searchMagIcon
        val searchMagIcon = searchView.findViewById<View>(android.support.v7.appcompat.R.id.search_mag_icon)
        (searchMagIcon.parent as ViewGroup).removeView(searchMagIcon)
    }
}