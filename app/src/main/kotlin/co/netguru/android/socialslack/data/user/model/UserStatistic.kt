package co.netguru.android.socialslack.data.user.model

data class UserStatistic(val name: String, val messages: Int, val avatarUrl: String) {

    companion object {
        fun User.toStatisticsView(messages: Int): UserStatistic {
            return UserStatistic(this.profile.firstName + " " + this.profile.lastName, messages, "")
        }
    }
}