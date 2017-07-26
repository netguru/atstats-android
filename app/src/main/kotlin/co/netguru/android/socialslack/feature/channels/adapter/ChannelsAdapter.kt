package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.channels.model.Channel

class ChannelsAdapter(private val onChannelClickListener: ChannelsViewHolder.ChannelClickListener) : RecyclerView.Adapter<ChannelsViewHolder>() {

    val channelsList: MutableList<Channel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChannelsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_channels, parent, false)
        val holder = ChannelsViewHolder(view)
        view.setOnClickListener { onChannelClickListener.onChannelClick(channelsList[holder.adapterPosition]) }
        return holder
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        holder.bind(channelsList[position])
    }

    override fun getItemCount() = channelsList.size

    fun addChannels(channelsList: List<Channel>) {
        this.channelsList.clear()
        this.channelsList.addAll(channelsList)
        notifyDataSetChanged()
    }
}