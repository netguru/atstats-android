package co.netguru.android.socialslack.data.filter

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption

import kotlin.Comparator

object ChannelsComparator {

    //TODO 13.07.2017 Comparators should be changed, when there will
    //TODO  possibility to obtain all needed information about the channel from SLACK API
    fun getChannelsComparatorForFilterOption(filterOption: ChannelsFilterOption): Comparator<ChannelStatistics> {
        when (filterOption) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> return getDescComparator()
            else -> return getAscComparator()
        }
    }

    private fun getAscComparator(): Comparator<ChannelStatistics> = Comparator { channelStatistics1, channelStatistics2 ->
        channelStatistics1.messageCount.
                compareTo(channelStatistics2.messageCount)
    }

    private fun getDescComparator(): Comparator<ChannelStatistics> = Comparator { channelStatistics1, channelStatistics2 ->
        channelStatistics2.messageCount.
                compareTo(channelStatistics1.messageCount)
    }
}