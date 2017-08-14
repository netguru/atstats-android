package co.netguru.android.socialslack.feature.share.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsViewHolder

class ShareChannelAdapter : RecyclerView.Adapter<ChannelsViewHolder>() {

    private val shareChannelList: MutableList<ChannelStatistics> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        return ChannelsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        //TODO 10.08.2017 Should be changed in task connected with sharing
        holder.bind(shareChannelList[position], ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
    }

    override fun getItemCount() = shareChannelList.size

    fun addChannels(shareChannelList: List<ChannelStatistics>) {
        this.shareChannelList.clear()
        this.shareChannelList.addAll(shareChannelList)
        notifyDataSetChanged()
    }
}