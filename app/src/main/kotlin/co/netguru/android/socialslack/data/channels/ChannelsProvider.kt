package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessages
import io.reactivex.Observable
import io.reactivex.Single


interface ChannelsProvider {

    fun getMessagesForChannel(channelId: String): Observable<ChannelMessages>

    fun getChannelsList(): Single<List<Channel>>
}