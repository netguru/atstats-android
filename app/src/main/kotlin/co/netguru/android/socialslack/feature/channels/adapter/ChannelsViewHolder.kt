package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.channels.model.Channel
import kotlinx.android.synthetic.main.item_channels.view.*

class ChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(channelPosition: Int, channel: Channel) {
        with(channel) {
            itemView.itemChannelsPlaceNrTextView.text = (channelPosition.toString() + '.')
            itemView.itemChannelsNameTextView.text = name
            itemView.itemChannelsMessagesNrTextView.text = messagesNumber.toString()
        }
    }
}