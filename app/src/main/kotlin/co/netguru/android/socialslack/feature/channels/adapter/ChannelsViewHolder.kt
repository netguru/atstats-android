package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.channels.model.Channel
import kotlinx.android.synthetic.main.item_channels.view.*

class ChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(channelPosition: Int, channel: Channel, onChannelClickListener: ChannelClickListener) {
        with(channel) {
            itemView.setOnClickListener { onChannelClickListener.onChannelClick(this) }
            itemView.itemChannelsPlaceNrTextView.text = (channelPosition.toString() + '.')
            itemView.itemChannelsNameTextView.text = name

            //TODO 10.07.2017 Change to messages number when it will be possible (according to SLACK API)
            itemView.itemChannelsMessagesNrTextView.text = membersNumber.toString()
        }
    }

    interface ChannelClickListener {
        fun onChannelClick(channel: Channel)
    }
}