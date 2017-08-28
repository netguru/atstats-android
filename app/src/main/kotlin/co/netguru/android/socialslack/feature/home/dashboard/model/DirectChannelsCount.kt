package co.netguru.android.socialslack.feature.home.dashboard.model

import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics


data class DirectChannelsCount(var sentMessages: Int = 0,
                               var receivedMessages: Int = 0) {

    fun accept(directChannelsStatistics: DirectChannelStatistics?): DirectChannelsCount {
        directChannelsStatistics?.apply {
            sentMessages += messagesFromUs
            receivedMessages += messagesFromOtherUser
        }
        return this
    }
}