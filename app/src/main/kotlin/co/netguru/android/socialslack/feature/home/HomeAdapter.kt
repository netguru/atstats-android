package co.netguru.android.socialslack.feature.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import co.netguru.android.socialslack.feature.home.channels.HomeChannelsFragment
import co.netguru.android.socialslack.feature.home.dashboard.HomeDashboardFragment
import co.netguru.android.socialslack.feature.home.users.HomeUsersFragment
import co.netguru.android.socialslack.feature.main.BlankFragment

class HomeAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> HomeUsersFragment()
        1 -> HomeChannelsFragment()
        2 -> HomeDashboardFragment()
        else -> throw UnsupportedOperationException()
    }

    override fun getCount(): Int {
        return 3
    }

}