package co.netguru.android.socialslack.feature.share.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsViewHolder

class ShareChannelAdapter : RecyclerView.Adapter<ChannelsViewHolder>() {

    private val shareChannelList: MutableList<Channel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        return ChannelsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        holder.bind(shareChannelList[position])
    }

    override fun getItemCount() = shareChannelList.size

    fun addChannels(shareChannelList: List<Channel>) {
        this.shareChannelList.clear()
        this.shareChannelList.addAll(shareChannelList)
        notifyDataSetChanged()
    }
}