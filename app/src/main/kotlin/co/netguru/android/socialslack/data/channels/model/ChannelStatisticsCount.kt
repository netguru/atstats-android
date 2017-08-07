package co.netguru.android.socialslack.data.channels.model


class ChannelStatisticsCount(val currentUser: String,
                             var hereCount: Int = 0,
                             var mentionsCount: Int = 0,
                             var myMessageCount: Int = 0,
                             var totalMessageCount: Int = 0) {

    fun accept(channelMessage: ChannelMessage?): ChannelStatisticsCount {
        channelMessage?.let {
            totalMessageCount++
            channelMessage.text?.apply {
                if (contains(ChannelMessage.HERE_TAG)) hereCount++
                if (contains(String.format(ChannelMessage.USER_MENTION, currentUser))) mentionsCount++
            }
            if (channelMessage.user?.contains(currentUser) ?: false) myMessageCount++
        }
        return this
    }
}