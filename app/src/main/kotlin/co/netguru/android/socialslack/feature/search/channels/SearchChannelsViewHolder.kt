package co.netguru.android.socialslack.feature.search.channels

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import kotlinx.android.synthetic.main.item_channels_search.view.*

class SearchChannelsViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_channels_search, parent, false)) {

    private val channelsNameTextView = itemView.itemChannelsSearchNameTextView
    private val channelsMessagesNrTextView = itemView.itemChannelsSearchMessagesNrTextView

    fun bind(channelStatistics: ChannelStatistics) {
        with(channelStatistics) {
            channelsNameTextView.text = channelName
            channelsMessagesNrTextView.text = messageCount.toString()
        }
    }
}