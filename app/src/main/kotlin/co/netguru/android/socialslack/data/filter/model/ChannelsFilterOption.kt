package co.netguru.android.socialslack.data.filter.model

enum class ChannelsFilterOption constructor(val value: String) {

    MOST_ACTIVE_CHANNEL("Most Active Channel"),
    CHANNEL_WE_ARE_MENTIONED_THE_MOST("Channel We Are Mentioned The Most"),
    CHANNEL_WE_ARE_MOST_ACTIVE("Channel We Are Most Active");


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