package co.netguru.android.atstats.data.filter.channels

import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption

object ChannelsMessagesNumberProvider {

    fun getProperMessagesNumber(filterOption: ChannelsFilterOption, channelsStatistics: ChannelStatistics) = when (filterOption) {
        ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> channelsStatistics.messageCount
        ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> channelsStatistics.mentionsCount
        ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> channelsStatistics.myMessageCount
    }
}