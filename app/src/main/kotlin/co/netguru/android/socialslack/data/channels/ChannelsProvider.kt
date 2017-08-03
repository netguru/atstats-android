package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import io.reactivex.Completable
import io.reactivex.Single


interface ChannelsProvider {

    fun getMessagesForChannel(channelId: String): Single<List<ChannelMessage>>

    fun getChannelsList(): Single<List<Channel>>

    fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable

    fun getMessagesFromApi(channelId: String, sinceTime: String): Single<List<ChannelMessage>>

    fun countChannelStatistics(channelId: String, channelName: String, user: String): Single<ChannelStatistics>
}