package co.netguru.android.socialslack.data.filter.directchannel

import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption


object DirectChannelsComparator {

    fun getFirectChannelComparatorForFilterOption(filterOption: UsersFilterOption) = when (filterOption) {
        UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> getComparatorForPersonWhoWeWriteTheMost()
        UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> getComparatorForPersonWhoWriteToUsTheMost()
        UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> getComparatorForPersonWhoWeTalkTheMost()
    }

    private fun getComparatorForPersonWhoWeWriteTheMost(): Comparator<DirectChannelStatistics> =
            Comparator { user1, user2 ->
                user2.messagesFromUs.compareTo(user1.messagesFromUs)
            }

    private fun getComparatorForPersonWhoWriteToUsTheMost(): Comparator<DirectChannelStatistics> =
            Comparator { user1, user2 ->
                user2.messagesFromOtherUser.compareTo(user1.messagesFromOtherUser)
            }

    private fun getComparatorForPersonWhoWeTalkTheMost(): Comparator<DirectChannelStatistics> =
            Comparator { user1, user2 ->
                (user2.messagesFromUs + user2.messagesFromOtherUser)
                        .compareTo(user1.messagesFromUs + user1.messagesFromOtherUser)
            }
}