package co.netguru.android.socialslack.data.user.model

import android.os.Parcel
import android.os.Parcelable
import co.netguru.android.socialslack.data.share.Sharable
import co.netguru.android.socialslack.data.user.profile.model.Presence
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
        fun User.toStatisticsView(sentMessages: Int, receivedMessages: Int,
                                  currentDayStreak: Int = 1): UserStatistic {
            return UserStatistic(this.id,
                    this.username ?: "",
                    this.firstName ?: "",
                    this.lastName ?: "",
                    this.realName ?: "",
                    sentMessages,
                    receivedMessages,
                    sentMessages + receivedMessages,
                    currentDayStreak,
                    this.profile.image512)
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