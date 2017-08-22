package co.netguru.android.socialslack.feature.share.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsViewHolder

class ShareChannelAdapter(private val channelsList: List<ChannelStatistics>, private val filterOption: ChannelsFilterOption)
    : RecyclerView.Adapter<ChannelsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChannelsViewHolder(parent)

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        holder.bind(channelsList[position], filterOption)
    }

    override fun getItemCount() = channelsList.size
}