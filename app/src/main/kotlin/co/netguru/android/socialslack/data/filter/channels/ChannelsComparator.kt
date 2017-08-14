package co.netguru.android.socialslack.data.filter.channels

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption

import kotlin.Comparator

object ChannelsComparator {

    fun getChannelsComparatorForFilterOption(filterOption: ChannelsFilterOption) = when (filterOption) {
        ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> getComparatorForMostActiveChannel()
        ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> getComparatorForChannelWeAreMentionedTheMost()
        ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> getComparatorForChannelWeAreMostActive()
    }

    private fun getComparatorForMostActiveChannel(): Comparator<ChannelStatistics> =
            Comparator { channelStatistics1, channelStatistics2 ->
                channelStatistics2.messageCount.compareTo(channelStatistics1.messageCount)
            }

    private fun getComparatorForChannelWeAreMentionedTheMost(): Comparator<ChannelStatistics> =
            Comparator { channelStatistics1, channelStatistics2 ->
                channelStatistics2.mentionsCount.compareTo(channelStatistics1.mentionsCount)
            }

    private fun getComparatorForChannelWeAreMostActive(): Comparator<ChannelStatistics> =
            Comparator { channelStatistics1, channelStatistics2 ->
                channelStatistics2.myMessageCount.compareTo(channelStatistics1.myMessageCount)
            }
}