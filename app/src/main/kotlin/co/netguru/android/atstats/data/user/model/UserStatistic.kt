package co.netguru.android.atstats.data.user.model

import android.os.Parcel
import android.os.Parcelable
import co.netguru.android.atstats.data.share.Sharable
import co.netguru.android.atstats.data.user.profile.model.Presence
import paperparcel.PaperParcel

@PaperParcel
data class UserStatistic(val id: String,
                         val username: String,
                         val firstName: String,
                         val lastName: String,
                         val name: String,
                         val sentMessages: Int,
                         val receivedMessages: Int,
                         val totalMessages: Int,
                         val currentDayStreak: Int,
                         val avatarUrl: String?,
                         var currentPositionInList: Int = 1,
                         var presence: Presence = Presence.AWAY) : Parcelable, Sharable {

    companion object {
        fun User.toStatisticsView(sentMessages: Int, receivedMessages: Int, isCurrentUser: Boolean,
                                  currentDayStreak: Int = 0): UserStatistic {
            return UserStatistic(this.id,
                    this.username ?: "",
                    this.firstName ?: "",
                    this.lastName ?: "",
                    this.realName ?: "",
                    if (isCurrentUser) sentMessages + receivedMessages else sentMessages,
                    if (isCurrentUser) sentMessages + receivedMessages else receivedMessages,
                    sentMessages + receivedMessages,
                    currentDayStreak,
                    this.profile?.image512)
        }

        @JvmField
        val CREATOR = PaperParcelUserStatistic.CREATOR
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelUserStatistic.writeToParcel(this, dest, flags)
    }

    override fun describeContents() = 0

    override fun id() = id

    override fun currentPositionInList() = currentPositionInList
}