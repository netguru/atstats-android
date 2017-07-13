package co.netguru.android.socialslack.data.filter.model

import co.netguru.android.socialslack.R

enum class ChannelsFilterOption constructor(val value: String, val textResId: Int) {

    MOST_ACTIVE_CHANNEL("mostActiveChannel", R.string.most_active_channel),
    CHANNEL_WE_ARE_MENTIONED_THE_MOST("channelWeAreMentionedTheMost", R.string.channel_we_are_mentioned_the_most),
    CHANNEL_WE_ARE_MOST_ACTIVE("channelWeAreMostActive", R.string.channel_we_are_most_active);

    companion object {

        fun getEnumForValue(value: String): ChannelsFilterOption {
            ChannelsFilterOption.values().forEach {
                if (it.value == value) {
                    return it
                }
            }

            throw IllegalArgumentException("There is no enum for $value")
        }
    }
}