package co.netguru.android.socialslack.feature.search.channels

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import java.lang.ref.WeakReference

class SearchChannelsAdapter : RecyclerView.Adapter<SearchChannelsViewHolder>() {

    private val channelsList = mutableListOf<ChannelStatistics>()
    private val channelsFilter by lazy {
        SearchChannelsFilter(channelsList.toList(), WeakReference(this))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchChannelsViewHolder(parent)

    override fun onBindViewHolder(holder: SearchChannelsViewHolder, position: Int) {
        holder.bind(channelsList[position])
    }

    override fun getItemCount() = channelsList.size

    internal fun addChannels(channelsList: List<ChannelStatistics>) {
        this.channelsList.clear()
        this.channelsList.addAll(channelsList)
        notifyDataSetChanged()
    }

    internal fun filterChannels(query: String) {
        channelsFilter.filter(query)
    }
}