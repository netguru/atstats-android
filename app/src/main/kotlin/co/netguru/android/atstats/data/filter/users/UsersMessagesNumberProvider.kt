package co.netguru.android.atstats.data.filter.users

import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.model.UserStatistic

object UsersMessagesNumberProvider {

    fun getProperMessagesNumber(filterOption: UsersFilterOption, userStatistic: UserStatistic) =
            when (filterOption) {
                UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> userStatistic.sentMessages
                UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> userStatistic.receivedMessages
                UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> userStatistic.totalMessages
            }
}