package co.netguru.android.atstats.feature.home.dashboard.model

import co.netguru.android.atstats.data.channels.model.ChannelStatistics


data class ChannelsCount(var totalMessagesReceived: Int = 0,
                         var totalMessageSent: Int = 0,
                         var totalMentions: Int = 0) {

    fun accept(channelStatistics: ChannelStatistics?): ChannelsCount {
        channelStatistics?.apply {
            totalMessagesReceived += (messageCount - myMessageCount)
            totalMessageSent += myMessageCount
            totalMentions += mentionsCount
        }
        return this
    }
}