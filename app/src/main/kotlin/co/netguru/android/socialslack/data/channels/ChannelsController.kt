package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsController @Inject constructor(private val channelsApi: ChannelsApi) {

    fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }
}