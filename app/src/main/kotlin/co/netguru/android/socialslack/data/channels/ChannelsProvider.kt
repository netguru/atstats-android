package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface ChannelsProvider {

    fun getMessagesForChannel(channelId: String): Observable<ChannelMessage>

    fun getChannelsList(): Single<List<Channel>>

    fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable
}