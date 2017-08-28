package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption

class ChannelsAdapter(private val onChannelClickListener: ChannelClickListener) : RecyclerView.Adapter<ChannelsViewHolder>() {

    internal val channelsList: MutableList<ChannelStatistics> = mutableListOf()

    internal lateinit var selectedFilterOption: ChannelsFilterOption

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        return ChannelsViewHolder(parent, onChannelClickListener)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        holder.bind(channelsList[position], selectedFilterOption)
    }

    override fun getItemCount() = channelsList.size

    fun addChannels(channelsList: List<ChannelStatistics>) {
        this.channelsList.clear()
        this.channelsList.addAll(channelsList)
        notifyDataSetChanged()
    }

    interface ChannelClickListener {
        fun onChannelClick(clickedItemPosition: Int)
    }
}