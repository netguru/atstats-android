package co.netguru.android.socialslack.data.filter.users

import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic

object UsersMessagesNumberProvider {

    fun getProperMessagesNumber(filterOption: UsersFilterOption, userStatistic: UserStatistic): Int {
        return if (userStatistic.isCurrentUser) {
            userStatistic.totalMessages
        } else {
            when (filterOption) {
                UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> userStatistic.sentMessages
                UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> userStatistic.receivedMessages
                UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> userStatistic.totalMessages
            }
        }
    }
}