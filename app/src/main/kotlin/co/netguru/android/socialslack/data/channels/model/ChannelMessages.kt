package co.netguru.android.socialslack.data.channels.model


data class ChannelMessages(val type: String, val ts: Float, val user: String, val text: String) {

    companion object {
        val MESSAGE_TYPE = "message"
        val HERE_TAG = "<!here>"
    }
}