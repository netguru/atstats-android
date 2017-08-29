package co.netguru.android.socialslack.feature.search.channels

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics

class SearchChannelsAdapter(private val channelsList: List<ChannelStatistics>)
    : RecyclerView.Adapter<SearchChannelsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchChannelsViewHolder(parent)

    override fun onBindViewHolder(holder: SearchChannelsViewHolder, position: Int) {
        holder.bind(channelsList[position])
    }

    override fun getItemCount() = channelsList.size
}