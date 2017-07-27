package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.Message
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DirectMessagesController @Inject constructor(private val directMessagesApi: DirectMessagesApi) {

    fun getDirectMessagesList(): Flowable<List<DirectChannel>> =
            directMessagesApi.getDirectMessagesList()
                    .map { it.channels }

    fun getMessagesInDirectChannel(channel: String, latest: String?, count: Int): Flowable<List<Message>> {
        if (latest == null) {
            return directMessagesApi.getDirectMessagesWithUser(channel, count)
                    .map { it.messages }
        } else {
            return directMessagesApi.getDirectMessagesWithUser(channel, count, latest, 0)
                    .map { it.messages }
        }
    }
}