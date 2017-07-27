package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessages
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsProviderImpl @Inject constructor(private val channelsApi: ChannelsApi) : ChannelsProvider {

    // TODO 27.07.2017 this should be get from the database
    override fun getMessagesForChannel(channelId: String):
            Observable<ChannelMessages> = channelsApi
            .getChannelsHistory(channelId, 1000, null, null, null, null)
            .flatMapObservable { it -> Observable.fromIterable(it.messageList) }

    override fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }
}