package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.channels.model.Channel
import kotlinx.android.synthetic.main.item_channels.view.*

class ChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(channel: Channel) {
        with(channel) {
            itemView.itemChannelsPlaceNrTextView.text = channel.currentPositionInList.toString()
            itemView.itemChannelsNameTextView.text = name

            //TODO 10.07.2017 Change to messages number when it will be possible (according to SLACK API)
            itemView.itemChannelsMessagesNrTextView.text = membersNumber.toString()
        }
    }
}