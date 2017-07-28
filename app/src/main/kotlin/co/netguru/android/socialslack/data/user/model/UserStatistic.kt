package co.netguru.android.socialslack.data.user.model

data class UserStatistic(val name: String, val messages: Int, val avatarUrl: String) {

    companion object {
        fun UserProfile.toStatisticsView(messages: Int): UserStatistic {
            return UserStatistic(this.firstName + " " + this.lastName, messages, this.image192)
        }
    }
}