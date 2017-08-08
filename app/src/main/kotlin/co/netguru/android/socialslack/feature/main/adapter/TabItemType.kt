package co.netguru.android.socialslack.feature.main.adapter

import co.netguru.android.socialslack.R

enum class TabItemType (val position: Int, val icon: Int, val title: Int){

    HOME(0, R.drawable.home_inactive, R.string.menu_home),
    CHANNELS(1, R.drawable.channels_inactive, R.string.menu_channels),
    USERS(2, R.drawable.users_inactive, R.string.menu_users),
    PROFILE(3, R.drawable.profile_inactive, R.string.menu_profile);

    companion object {
        fun getTabItemByPosition(position: Int): TabItemType {
            values().forEach { item ->
                       if (item.position == position) {
                           return item
                       }
                     }

            throw IllegalArgumentException("There is no element with position $position")
        }
    }


}

