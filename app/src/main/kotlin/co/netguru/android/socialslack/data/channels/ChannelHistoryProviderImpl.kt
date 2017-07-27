package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.ChannelMessages
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelHistoryProviderImpl @Inject constructor() : ChannelHistoryProvider{

    override fun getMessagesForChannel(channelId: String): Observable<ChannelMessages> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}