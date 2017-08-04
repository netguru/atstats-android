package co.netguru.android.socialslack.feature.main.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import co.netguru.android.socialslack.feature.channels.root.ChannelsRootFragment
import co.netguru.android.socialslack.feature.home.HomeFragment
import co.netguru.android.socialslack.feature.main.BlankFragment
import co.netguru.android.socialslack.feature.users.root.UsersRootFragment

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = when (TabItemType.getTabItemByPosition(position)) {
        TabItemType.HOME -> HomeFragment.newInstance()
        TabItemType.CHANNELS -> ChannelsRootFragment.newInstance()
        TabItemType.USERS -> UsersRootFragment.newInstance()
        TabItemType.PROFILE -> BlankFragment.newInstance()
    }

    override fun getCount(): Int {
        return TabItemType.values().size
    }
}
