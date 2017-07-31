package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsProviderImpl @Inject constructor(private val channelsApi: ChannelsApi) : ChannelsProvider {

    // TODO 27.07.2017 REMOVE THIS MOCK
    companion object {
        val MOCK_COUNT = 1000
    }

    // TODO 27.07.2017 this should be get from the database
    override fun getMessagesForChannel(channelId: String):
            Observable<ChannelMessage> = channelsApi
            .getChannelsHistory(channelId, MOCK_COUNT, null, null, null, null)
            .flatMapObservable { it -> Observable.fromIterable(it.messageList) }

    override fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }
}