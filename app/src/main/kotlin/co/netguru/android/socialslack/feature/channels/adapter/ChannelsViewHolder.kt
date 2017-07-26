package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.channels.model.Channel
import kotlinx.android.synthetic.main.item_channels.view.*

class ChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val POSITION_FIRST = 1
    }

    private val channelsPlaceNrTextView = itemView.itemChannelsPlaceNrTextView
    private val channelsNameTextView = itemView.itemChannelsNameTextView
    private val channelsMessagesNrTextView = itemView.itemChannelsMessagesNrTextView
    private val channelsRankImageView = itemView.itemChannelsRankImageView

    fun bind(channel: Channel, onChannelClickListener: ChannelClickListener) {
        with(channel) {
            itemView.setOnClickListener { onChannelClickListener.onChannelClick(this) }
            channelsPlaceNrTextView.text = (currentPositionInList.toString() + '.')
            channelsNameTextView.text = name

            //TODO 10.07.2017 Change to messages number when it will be possible (according to SLACK API)
            channelsMessagesNrTextView.text = membersNumber.toString()
            changeRankViewVisibility(currentPositionInList)
        }
    }

    private fun changeRankViewVisibility(channelsPositionInList: Int) {
        channelsRankImageView.visibility = if (channelsPositionInList == POSITION_FIRST)
            View.VISIBLE else View.GONE
    }

    interface ChannelClickListener {
        fun onChannelClick(channel: Channel)
    }
}