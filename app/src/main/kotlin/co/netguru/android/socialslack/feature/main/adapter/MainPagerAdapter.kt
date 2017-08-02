package co.netguru.android.socialslack.feature.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.channels.root.ChannelsRootFragment
import co.netguru.android.socialslack.feature.home.HomeFragment
import co.netguru.android.socialslack.feature.main.BlankFragment
import co.netguru.android.socialslack.feature.users.profile.UsersProfileFragment

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    companion object {
        private const val MOCKED_USER_PROFILE_COUNT = 15
        private const val MOCKED_USER_PROFILE_POSITION = 3
    }

    override fun getItem(position: Int): Fragment {
        when (TabItemType.getTabItemByPosition(position)) {
            TabItemType.HOME -> return HomeFragment.newInstance()
            TabItemType.CHANNELS -> return ChannelsRootFragment.newInstance()
        //TODO 02.08.2017 Replace with users list fragment
            TabItemType.USERS -> return UsersProfileFragment.newInstance(getMockedUsersProfileList(),
                    MOCKED_USER_PROFILE_POSITION)
            TabItemType.PROFILE -> return BlankFragment.newInstance()
            else -> throw IllegalArgumentException("There is no fragment for the position $position")
        }
    }

    override fun getCount(): Int {
        return TabItemType.values().size
    }

    private fun getMockedUsersProfileList(): Array<UserStatistic> {
        val list = mutableListOf<UserStatistic>()
        for (i in 0..MOCKED_USER_PROFILE_COUNT) {
            list += UserStatistic("rafal.adasiewicz",
                    "Rafal Adasiewicz",
                    350,
                    50,
                    300,
                    350,
                    7,
                    "https://avatars.slack-edge.com/2016-10-10/89333489734_47e72c65c34236dcff70_192.png"
            )
        }

        return list.toTypedArray()
    }
}
