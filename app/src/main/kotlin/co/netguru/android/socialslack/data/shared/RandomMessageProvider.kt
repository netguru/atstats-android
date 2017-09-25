package co.netguru.android.socialslack.data.shared

import java.util.*

object RandomMessageProvider {

    fun getRandomMessageFromArray(messages: Array<String>): String {
        val randomPosition = Random().nextInt(messages.size)

        return messages[randomPosition]
    }
}