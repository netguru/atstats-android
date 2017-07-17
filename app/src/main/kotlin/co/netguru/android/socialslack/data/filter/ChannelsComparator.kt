package co.netguru.android.socialslack.data.filter

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import java.util.Comparator

object ChannelsComparator {

    //TODO 13.07.2017 Comparators should be changed, when there will
    //TODO  possibility to obtain all needed information about the channel from SLACK API
    fun getChannelsComparatorForFilterOption(filterOption: ChannelsFilterOption): Comparator<Channel> {
        when (filterOption) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> return getAscComparator()
            else -> return getDescComparator()
        }
    }

    private fun getAscComparator(): Comparator<Channel> = Comparator { channel1, channel2 ->
        channel1.membersNumber.
                compareTo(channel2.membersNumber)
    }

    private fun getDescComparator(): Comparator<Channel> = Comparator { channel1, channel2 ->
        channel2.membersNumber.
                compareTo(channel1.membersNumber)
    }
}