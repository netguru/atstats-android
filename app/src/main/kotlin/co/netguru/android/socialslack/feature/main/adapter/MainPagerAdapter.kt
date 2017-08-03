package co.netguru.android.socialslack.feature.main.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.channels.root.ChannelsRootFragment
import co.netguru.android.socialslack.feature.home.HomeFragment
import co.netguru.android.socialslack.feature.main.BlankFragment
import co.netguru.android.socialslack.feature.users.profile.UsersProfileFragment

class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    //TODO 02.08.2017 Remove when database will be ready
    companion object {
        private const val MOCKED_USER_PROFILE_COUNT = 15
        private const val MOCKED_USER_PROFILE_POSITION = 3
    }

    override fun getItem(position: Int) = when (TabItemType.getTabItemByPosition(position)) {
        TabItemType.HOME -> HomeFragment.newInstance()
        TabItemType.CHANNELS -> ChannelsRootFragment.newInstance()
        TabItemType.USERS -> UsersProfileFragment.newInstance(getMockedUsersProfileList(),
                MOCKED_USER_PROFILE_POSITION)
        TabItemType.PROFILE -> BlankFragment.newInstance()
    }

    override fun getCount(): Int {
        return TabItemType.values().size
    }

    private fun getMockedUsersProfileList(): Array<UserStatistic> {
        val list = mutableListOf<UserStatistic>()
        for (i in 0..MOCKED_USER_PROFILE_COUNT) {
            list += UserStatistic("U2JHH3HAA",
                    "rafal.adasiewicz",
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
