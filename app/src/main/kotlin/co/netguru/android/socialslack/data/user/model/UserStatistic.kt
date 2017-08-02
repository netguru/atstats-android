package co.netguru.android.socialslack.data.user.model

//TODO 02.08.2017 Refactor this model when database will be ready

data class UserStatistic(val username: String,
                         val name: String,
                         val messages: Int,
                         val sentMessages: Int,
                         val receivedMessages: Int,
                         val totalMessages: Int,
                         val currentDayStreak: Int,
                         val avatarUrl: String,
                         val isActive: Boolean = false) {

    companion object {
        fun User.toStatisticsView(messages: Int, sentMessages: Int = 350, receivedMessages: Int = 100,
                                  currentDayStreak: Int = 1): UserStatistic {
            return UserStatistic(this.username,
                    this.realName,
                    messages,
                    sentMessages,
                    receivedMessages,
                    sentMessages + receivedMessages,
                    currentDayStreak,
                    this.profile.image192)
        }
    }
}