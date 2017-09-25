package co.netguru.android.socialslack.data.filter.users

import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic

object UsersStatisticsNumberChecker {

    fun checkStatisticsNumberForSelectedFilter(filterOption: UsersFilterOption, userStatistic: UserStatistic) = when (filterOption) {
        UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> userStatistic.sentMessages > 0
        UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> userStatistic.receivedMessages > 0
        UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> userStatistic.totalMessages > 0
    }
}