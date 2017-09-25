package co.netguru.android.atstats.feature.search.channels

import android.widget.Filter
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import java.lang.ref.WeakReference

class SearchChannelsFilter(private val channelsList: List<ChannelStatistics>,
                           private val adapter: WeakReference<SearchChannelsAdapter>) : Filter() {

    override fun performFiltering(constraint: CharSequence)  = FilterResults().apply {
        val filteredList = channelsList.filter { it.channelName.toLowerCase().contains(constraint.toString().toLowerCase()) }

        values = filteredList
        count = filteredList.size
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        val filteredList = (results.values as List<*>).filterIsInstance(ChannelStatistics::class.java)
        adapter.get()?.addChannels(filteredList)
    }
}