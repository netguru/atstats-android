package co.netguru.android.socialslack.feature.home.channels

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import kotlinx.android.synthetic.main.item_channel.view.*

class HomeChannelsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(channelStatistics: ChannelStatistics) {
        // TODO 11.07.17 remove mock data
        itemView.channelNameTextView.text = channelStatistics.channelName
        itemView.channelMessagesTextView.text = "234 532"
    }
}