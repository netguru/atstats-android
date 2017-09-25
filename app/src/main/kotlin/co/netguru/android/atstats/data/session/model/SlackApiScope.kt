package co.netguru.android.atstats.data.session.model

enum class SlackApiScope constructor(val value: String) {

    CHANNELS_HISTORY("channels:history"),
    CHANNELS_READ("channels:read"),
    DO_NOT_DISTURB_READ("dnd:read"),
    EMOJI_READ("emoji:read"),
    FILES_READ("files:read"),
    FILES_WRITE("files:write:user"),
    GROUPS_HISTORY("groups:history"),
    GROUPS_READ("groups:read"),
    MESSAGES_HISTORY("im:history"),
    MESSAGES_READ("im:read"),
    MESSAGES_HISTORY_MULTIPARTY("mpim:history"),
    MESSAGES_READ_MULTIPARTY("mpim:read"),
    PINS_READ("pins:read"),
    REACTIONS_READ("reactions:read"),
    REMINDERS_READ("reminders:read"),
    SEARCH_READ("search:read"),
    STARS_READ("stars:read"),
    TEAM_READ("team:read"),
    USER_GROUPS_READ("usergroups:read"),
    USERS_PROFILE("users.profile:read"),
    USERS_READ("users:read");

    companion object {
        private val DIVIDER = ','

        fun getSlackApiScope(): String {
            var slackApiScope = ""
            SlackApiScope.values().forEach {
                slackApiScope += it.value + DIVIDER
            }

            return slackApiScope
        }
    }
}