package co.netguru.android.socialslack.feature.home

import android.support.v4.app.Fragment
import co.netguru.android.socialslack.feature.home.channels.HomeChannelsFragment
import co.netguru.android.socialslack.feature.home.dashboard.HomeDashboardFragment
import co.netguru.android.socialslack.feature.home.users.HomeUsersFragment

enum class HomeScreen(val position: Int, val newInstance: () -> Fragment) {
    USERS(0, { HomeUsersFragment.newInstance() }),
    CHANNELS(1, { HomeChannelsFragment.newInstance() }),
    DASHBOARD(2, { HomeDashboardFragment.newInstance() });

    companion object {
        fun getScreenAtPosition(position: Int): HomeScreen {
            HomeScreen.values().forEach { item ->
                if (item.position == position) {
                    return item
                }
            }

            throw IllegalArgumentException("There is no element with position $position")
        }
    }
}