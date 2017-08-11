package co.netguru.android.socialslack.data.filter.users

import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic

object UsersComparator {

    fun getUsersComparatorForFilterOption(filterOption: UsersFilterOption) = when (filterOption) {
        UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> getComparatorForPersonWhoWeWriteTheMost()
        UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> getComparatorForPersonWhoWriteToUsTheMost()
        UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> getComparatorForPersonWhoWeTalkTheMost()
    }

    private fun getComparatorForPersonWhoWeWriteTheMost(): Comparator<UserStatistic> =
            Comparator { user1, user2 ->
                user2.sentMessages.compareTo(user1.sentMessages)
            }

    private fun getComparatorForPersonWhoWriteToUsTheMost(): Comparator<UserStatistic> =
            Comparator { user1, user2 ->
                user2.receivedMessages.compareTo(user1.receivedMessages)
            }

    private fun getComparatorForPersonWhoWeTalkTheMost(): Comparator<UserStatistic> =
            Comparator { user1, user2 ->
                user2.totalMessages.compareTo(user1.totalMessages)
            }
}