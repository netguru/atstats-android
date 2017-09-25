package co.netguru.android.atstats.data.user.profile.model

import co.netguru.android.atstats.data.user.model.UserDB


class UserWithPresence(val username: String?,
                       val firstName: String?,
                       val lastName: String?,
                       val avatarUrl: String,
                       val presence: Presence) {

    companion object {
        fun createUserDBWithPresence(user: UserDB, presence: Presence = Presence.AWAY): UserWithPresence =
                UserWithPresence(user.username, user.firstName, user.lastName, user.avatarUrl, presence)
    }
}