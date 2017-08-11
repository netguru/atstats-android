package co.netguru.android.socialslack.data.direct.model


data class DirectChannelStatisticsCount(var otherUserId: String,
                                        var messagesFromUs: Int = 0,
                                        var messagesFromOtherUser: Int = 0) {

    fun accept(directMessage: DirectMessage?): DirectChannelStatisticsCount {
        directMessage?.apply {
            user?.let {
                if (user == otherUserId) messagesFromOtherUser++ else messagesFromUs++
            }
        }
        return this
    }
}