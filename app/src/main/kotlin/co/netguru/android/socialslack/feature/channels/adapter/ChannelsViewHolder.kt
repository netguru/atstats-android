package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.R
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

    fun bind(channel: Channel) {
        with(channel) {
            channelsPlaceNrTextView.text = (currentPositionInList.toString() + '.')
            channelsNameTextView.text = name

            //TODO 10.07.2017 Change to messages number when it will be possible (according to SLACK API)
            channelsMessagesNrTextView.text = membersNumber.toString()
            changeRankViewVisibility(currentPositionInList)
            changeMessagesNrTextColor(currentPositionInList)
        }
    }

    private fun changeRankViewVisibility(channelsPositionInList: Int) {
        channelsRankImageView.visibility = if (channelsPositionInList == POSITION_FIRST)
            View.VISIBLE else View.GONE
    }

    private fun changeMessagesNrTextColor(channelsPositionInList: Int) {
        channelsMessagesNrTextView.setTextColor(ContextCompat.getColor(itemView.context,
                if (channelsPositionInList == POSITION_FIRST) R.color.orange else R.color.primary))
    }

    interface ChannelClickListener {
        fun onChannelClick(channel: Channel)
    }
}