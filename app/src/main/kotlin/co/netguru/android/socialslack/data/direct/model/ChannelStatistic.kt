package co.netguru.android.socialslack.data.direct.model

data class ChannelStatistic(val channel: DirectChannel,
                            val messagesFromUs: Int,
                            val messagesFromOtherUser: Int)