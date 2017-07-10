package co.netguru.android.socialslack.feature.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import co.netguru.android.socialslack.feature.channels.ChannelsFragment
import co.netguru.android.socialslack.feature.main.BlankFragment

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (TabItemType.getTabItemByPosition(position)) {
            TabItemType.HOME -> return BlankFragment.newInstance()
            TabItemType.CHANNELS -> return ChannelsFragment.newInstance()
            TabItemType.USERS -> return BlankFragment.newInstance()
            TabItemType.PROFILE -> return BlankFragment.newInstance()
            else -> throw IllegalArgumentException("There is no fragment for the position $position")
        }
    }

    override fun getCount(): Int {
        return TabItemType.values().size
    }

}
