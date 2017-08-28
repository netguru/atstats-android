package co.netguru.android.socialslack.feature.search

import android.os.Bundle
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.customTheme.CustomThemeActivity

class SearchActivity : CustomThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val fragment = supportFragmentManager.findFragmentById(R.id.searchFragmentContainer)
                ?: SearchFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, fragment)
                .commit()
    }
}