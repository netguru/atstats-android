package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics

class ChannelsAdapter(private val onChannelClickListener: ChannelsViewHolder.ChannelClickListener) : RecyclerView.Adapter<ChannelsViewHolder>() {

    val channelsList: MutableList<ChannelStatistics> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        return ChannelsViewHolder(parent, onChannelClickListener)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        holder.bind(channelsList[position])
    }

    override fun getItemCount() = channelsList.size

    fun addChannels(channelsList: List<ChannelStatistics>) {
        this.channelsList.clear()
        this.channelsList.addAll(channelsList)
        notifyDataSetChanged()
    }
}