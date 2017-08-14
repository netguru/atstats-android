package co.netguru.android.socialslack.common.util


object TimeAndCountUtil {

    const val MESSAGE_COUNT = 1000
    const val SINCE_TIME: Long = 60 * 60 * 24 * 30 // 30 days in seconds
    fun currentTimeInSeconds() = System.currentTimeMillis() / 1000
}