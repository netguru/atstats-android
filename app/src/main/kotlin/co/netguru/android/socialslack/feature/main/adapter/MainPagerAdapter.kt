package co.netguru.android.socialslack.feature.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import co.netguru.android.socialslack.feature.channels.root.ChannelsRootFragment
import co.netguru.android.socialslack.feature.home.HomeFragment
import co.netguru.android.socialslack.feature.main.BlankFragment
import co.netguru.android.socialslack.feature.users.profile.UsersProfileFragment

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (TabItemType.getTabItemByPosition(position)) {
            TabItemType.HOME -> return HomeFragment.newInstance()
            TabItemType.CHANNELS -> return ChannelsRootFragment.newInstance()
        //TODO 02.08.2017 Replace with users list fragment
            TabItemType.USERS -> return UsersProfileFragment.newInstance()
            TabItemType.PROFILE -> return BlankFragment.newInstance()
            else -> throw IllegalArgumentException("There is no fragment for the position $position")
        }
    }

    override fun getCount(): Int {
        return TabItemType.values().size
    }

}
