package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.Message
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DirectMessagesController @Inject constructor(private val directMessagesApi: DirectMessagesApi) {

    companion object {
        const val MESSAGES_INCLUSIVE = 0
    }

    fun getDirectMessagesList(): Single<List<DirectChannel>> =
            directMessagesApi.getDirectMessagesList()
                    .map { it.channels }

    fun getMessagesInDirectChannel(channel: String, latest: String?, count: Int): Single<List<Message>> {
        if (latest == null) {
            return directMessagesApi.getDirectMessagesWithUser(channel, count)
                    .map { it.messages }
        } else {
            return directMessagesApi.getDirectMessagesWithUser(channel, count, latest, MESSAGES_INCLUSIVE)
                    .map { it.messages }
        }
    }
}