package co.netguru.android.socialslack.feature.home.channels

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.channels.model.Channel
import kotlinx.android.synthetic.main.item_channel.view.*

class HomeChannelsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(channel: Channel) {
        // TODO 11.07.17 remove mock data
        itemView.channelNameTextView.text = channel.name
        itemView.channelMessagesTextView.text = "234 532"
    }
}