package co.netguru.android.socialslack.data.user.model

import android.os.Parcel
import android.os.Parcelable
import co.netguru.android.socialslack.data.share.Sharable
import co.netguru.android.socialslack.data.user.profile.Presence
import paperparcel.PaperParcel

//TODO 02.08.2017 Refactor this model when database will be ready
@PaperParcel
data class UserStatistic(val id: String,
                         val username: String,
                         val name: String,
                         val messages: Int,
                         val sentMessages: Int,
                         val receivedMessages: Int,
                         val totalMessages: Int,
                         val currentDayStreak: Int,
                         val avatarUrl: String?,
                         var currentPositionInList: Int = 1,
                         var presence: Presence = Presence.AWAY) : Parcelable, Sharable {

    companion object {
        //TODO 02.08.2017 Remove those mocked values when database will be ready
        fun User.toStatisticsView(messages: Int, sentMessages: Int = 350, receivedMessages: Int = 100,
                                  currentDayStreak: Int = 1): UserStatistic {
            return UserStatistic(this.id,
                    this.username,
                    this.realName ?: "",
                    messages,
                    sentMessages,
                    receivedMessages,
                    sentMessages + receivedMessages,
                    currentDayStreak,
                    this.profile.image512)
        }

        @JvmField val CREATOR = PaperParcelUserStatistic.CREATOR
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelUserStatistic.writeToParcel(this, dest, flags)
    }

    override fun describeContents() = 0

    override fun id() = id

    override fun currentPositionInList() = currentPositionInList
}