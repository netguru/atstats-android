package co.netguru.android.socialslack.feature.search.channels

import android.widget.Filter
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics

class SearchChannelsFilter(private val channelsList: List<ChannelStatistics>,
                           private val adapter: SearchChannelsAdapter) : Filter() {

    private val filteredChannelsList = mutableListOf<ChannelStatistics>()

    override fun performFiltering(constraint: CharSequence): FilterResults {
        filteredChannelsList.clear()
        val filterResults = FilterResults()

        channelsList.filter { it.channelName.toLowerCase().contains(constraint.toString().toLowerCase()) }
                .forEach { filteredChannelsList.add(it) }

        filterResults.values = filteredChannelsList
        filterResults.count = filteredChannelsList.size

        return filterResults
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        adapter.addChannels(filteredChannelsList)
    }
}