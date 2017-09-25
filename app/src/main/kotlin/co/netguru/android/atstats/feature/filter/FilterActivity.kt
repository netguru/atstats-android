package co.netguru.android.atstats.feature.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.netguru.android.atstats.R
import co.netguru.android.atstats.common.customTheme.CustomThemeActivity
import co.netguru.android.atstats.common.extensions.getAttributeColor
import co.netguru.android.atstats.data.filter.model.FilterObjectType
import kotlinx.android.synthetic.main.activity_main.*

class FilterActivity : CustomThemeActivity() {

    companion object {
        const val FILTER_OBJECT_TYPE = "filterObjectType"

        fun startActivity(context: Context, filterObjectType: FilterObjectType) {
            val intent = Intent(context, FilterActivity::class.java)
            intent.putExtra(FilterActivity.FILTER_OBJECT_TYPE, filterObjectType.name)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        initializeToolbar()

        val fragment = supportFragmentManager.findFragmentById(R.id.filterFragmentContainer)
                ?: FilterFragment.newInstance(FilterObjectType.valueOf(intent.getStringExtra(FILTER_OBJECT_TYPE)))

        supportFragmentManager.beginTransaction()
                .replace(R.id.filterFragmentContainer, fragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter_activity, menu)
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
        supportActionBar?.title = getString(R.string.categories)
    }
}