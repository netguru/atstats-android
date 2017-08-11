package co.netguru.android.socialslack.data.filter.channels

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption

object ChannelsPositionUpdater {

    fun updateChannelsPositionInList(channelsList: List<ChannelStatistics>, channelsFilterOption: ChannelsFilterOption) {
        if (channelsList.isEmpty()) {
            return
        }
        var currentPositionInList = 1
        channelsList[0].currentPositionInList = currentPositionInList
        for (i in 1.until(channelsList.size)) {
            if (compareUsersDependsOnFilterOption(channelsFilterOption, channelsList[i], channelsList[i - 1])) {
                channelsList[i].currentPositionInList = currentPositionInList
            } else {
                channelsList[i].currentPositionInList = ++currentPositionInList
            }
        }
    }

    private fun compareUsersDependsOnFilterOption(filterOption: ChannelsFilterOption,
                                                  firstChannel: ChannelStatistics,
                                                  secondChannel: ChannelStatistics) = when (filterOption) {
        ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> firstChannel.myMessageCount == secondChannel.myMessageCount
        ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> firstChannel.mentionsCount == secondChannel.mentionsCount
        ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> firstChannel.messageCount == secondChannel.messageCount
    }
}