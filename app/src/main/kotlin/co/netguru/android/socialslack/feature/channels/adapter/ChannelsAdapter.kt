package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.channels.model.Channel

class ChannelsAdapter : RecyclerView.Adapter<ChannelsViewHolder>() {

    //TODO 10.07.2017 Should be refactored while integrating channels API
    var currentChannelPosition: Int = 1

    var channelsList: MutableList<Channel> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChannelsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_channels, parent, false)
        return ChannelsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder?, position: Int) {
        holder?.bind(currentChannelPosition, channelsList[position])
    }

    override fun getItemCount() = channelsList.size
}