package co.netguru.android.socialslack.data.filter.channels

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption

object ChannelsStatisticsNumberChecker {

    fun checkStatisticsNumberForFilterOption(filterOption: ChannelsFilterOption, channelsStatistics: ChannelStatistics) = when (filterOption) {
        ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> channelsStatistics.messageCount > 0
        ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> channelsStatistics.mentionsCount > 0
        ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> channelsStatistics.myMessageCount > 0
    }
}