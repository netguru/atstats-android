package co.netguru.android.atstats.data.user.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserDB(@PrimaryKey val id: String,
                  val username: String?,
                  val firstName: String?,
                  val lastName: String?,
                  val realName: String?,
                  val avatarUrl: String) {

    companion object {
        fun createUserDB(user: User): UserDB =
                UserDB(user.id,
                        user.username,
                        user.profile.firstName,
                        user.profile.lastName,
                        user.realName,
                        user.profile.image512)
    }
}