package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.session.TokenRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsController @Inject constructor(private val tokenRepository: TokenRepository,
                                             private val channelsApi: ChannelsApi) {

    fun getChannelsList(): Single<List<Channel>> =
            tokenRepository.getToken()
                    .flatMap { channelsApi.getChannelsList(it.accessToken) }
                    .map { it.channelList }
}