package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.ChannelMessages
import io.reactivex.Observable


interface ChannelHistoryProvider {

    fun getMessagesForChannel(channelId: String): Observable<ChannelMessages>
}