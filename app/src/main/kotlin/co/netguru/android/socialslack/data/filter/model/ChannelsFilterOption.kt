package co.netguru.android.socialslack.data.filter.model

import android.support.annotation.StringRes
import co.netguru.android.socialslack.R

enum class ChannelsFilterOption constructor(@StringRes val textResId: Int) : Filter {

    MOST_ACTIVE_CHANNEL(R.string.most_active_channel_filter) {
        override fun filterName() = name
    },
    CHANNEL_WE_ARE_MENTIONED_THE_MOST(R.string.channel_we_are_mentioned_the_most) {
        override fun filterName() = name
    },
    CHANNEL_WE_ARE_MOST_ACTIVE(R.string.channel_we_are_most_active) {
        override fun filterName() = name
    };
}