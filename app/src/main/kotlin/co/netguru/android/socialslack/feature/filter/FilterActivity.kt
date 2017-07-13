package co.netguru.android.socialslack.feature.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import co.netguru.android.socialslack.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class FilterActivity : AppCompatActivity() {

    companion object {
        const val FILTER_OBJECT_TYPE = "filterObjectType"

        fun startActivity(context: Context, filterObjectType: FilterObjectType) {
            val intent = Intent(context, FilterActivity::class.java)
            intent.putExtra(FilterActivity.FILTER_OBJECT_TYPE, filterObjectType)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        initializeToolbar()

        val fragment = supportFragmentManager.findFragmentById(R.id.filterFragmentContainer)
                ?: FilterFragment.newInstance(intent.getSerializableExtra(FILTER_OBJECT_TYPE)
                as FilterObjectType)

        supportFragmentManager.beginTransaction()
                .replace(R.id.filterFragmentContainer, fragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter_activity, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionApply -> {
                MainActivity.startActivityWithRequest(this, MainActivity.REQUEST_SORT_CHANNELS)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initializeToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Categories"
    }
}