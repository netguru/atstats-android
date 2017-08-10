package co.netguru.android.socialslack.data.filter

import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic

object UsersComparator {

    fun getUsersComparatorForFilterOption(filterOption: UsersFilterOption) : Comparator<UserStatistic> {
        when (filterOption) {
            UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> return getComparatorForPersonWhoWeWriteTheMost()
            UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> return getComparatorForPersonWhoWriteToUsTheMost()
            UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> return getComparatorForPersonWhoWeTalkTheMost()
        }
    }

    private fun getComparatorForPersonWhoWeWriteTheMost() : Comparator<UserStatistic> =
            Comparator { user1, user2 -> user2.sentMessages.compareTo(user1.sentMessages)
    }

    private fun getComparatorForPersonWhoWriteToUsTheMost() : Comparator<UserStatistic> =
            Comparator { user1, user2 -> user2.receivedMessages.compareTo(user1.receivedMessages)
    }

    private fun getComparatorForPersonWhoWeTalkTheMost() : Comparator<UserStatistic> =
            Comparator { user1, user2 -> user2.totalMessages.compareTo(user1.totalMessages)
    }
}