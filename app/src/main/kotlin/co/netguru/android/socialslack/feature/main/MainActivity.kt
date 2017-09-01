package co.netguru.android.socialslack.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.view.MenuItem
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.customTheme.CustomThemeActivity
import co.netguru.android.socialslack.common.extensions.getAttributeColor
import co.netguru.android.socialslack.feature.channels.root.ChannelsRootFragment
import co.netguru.android.socialslack.feature.home.HomeFragment
import co.netguru.android.socialslack.feature.profile.ProfileFragment
import co.netguru.android.socialslack.feature.users.root.UsersRootFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CustomThemeActivity() {

    companion object {
        const val REQUEST_SORT_CHANNELS = 101
        const val REQUEST_SORT_USERS = 102
        const val REQUEST_EXTRA = "requestExtra"
        const val SHOW_PROFILE_KEY = "key:showProfile"

        private const val REQUEST_DEFAULT = 0

        fun startActivityWithRequest(context: Context, requestCode: Int) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(MainActivity.REQUEST_EXTRA, requestCode)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }

        fun startActivityOnProfile(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(SHOW_PROFILE_KEY, true)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeToolbar()
        initializeBottomNavigationView()
        if (intent.getBooleanExtra(SHOW_PROFILE_KEY, false)) showProfile()
            else replaceFragmentInMainContainer(HomeFragment.newInstance())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        when (intent.getIntExtra(REQUEST_EXTRA, REQUEST_DEFAULT)) {
            REQUEST_SORT_CHANNELS -> refreshDataOnChannelsFragment()
            REQUEST_SORT_USERS -> refreshDataOnUsersFragment()
            else -> throw IllegalStateException("Intent should contains REQUEST_EXTRA")
        }
    }

    // Workaround for proper BackStack support in nested fragment, because of:
    // https://issuetracker.google.com/issues/36959108
    // https://issuetracker.google.com/issues/37123225
    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment != null && fragment.isVisible) {
                val childFragmentManager = fragment.childFragmentManager
                if (childFragmentManager.backStackEntryCount > 0) {
                    childFragmentManager.popBackStack()
                    showMainActionBar()
                    return
                }
            }
        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            this.onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun showMainActionBar() {
        supportActionBar?.setLogo(R.drawable.toolbar_icon_padding)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun showHomeUpEnableActionBar() {
        supportActionBar?.setLogo(null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun refreshDataOnChannelsFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)
        if (fragment is ChannelsRootFragment) {
            fragment.sortChannelsData()
        }
    }

    private fun refreshDataOnUsersFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)
        if (fragment is UsersRootFragment) {
            fragment.sortUsersData()
        }
    }

    private fun showProfile() {
        mainNavigationView.selectedItemId = R.id.menu_profile
    }

    private fun initializeBottomNavigationView() {
        mainNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> replaceFragmentInMainContainer(HomeFragment.newInstance())
                R.id.menu_channels -> replaceFragmentInMainContainer(ChannelsRootFragment.newInstance())
                R.id.menu_users -> replaceFragmentInMainContainer(UsersRootFragment.newInstance())
                R.id.menu_profile -> replaceFragmentInMainContainer(ProfileFragment.newInstance())
                else -> throw IllegalStateException("No action specified for item: $it")
            }
        }
    }

    private fun replaceFragmentInMainContainer(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, fragment)
                .commit()

        return true
    }

    // Suppress RestrictedApi warning because of this bug https://issuetracker.google.com/issues/37130193
    @SuppressLint("RestrictedApi")
    private fun initializeToolbar() {
        // Not working when setting up the background color from attrs in styles
        toolbar.setBackgroundColor(this.getAttributeColor(R.attr.colorPrimary))
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.let {
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDefaultDisplayHomeAsUpEnabled(false)
        }
    }
}