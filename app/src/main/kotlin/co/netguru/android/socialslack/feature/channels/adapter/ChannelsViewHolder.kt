package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import kotlinx.android.synthetic.main.item_channels.view.*

class ChannelsViewHolder(parent: ViewGroup, private val onChannelClickListener: ChannelClickListener? = null)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_channels, parent, false)) {

    companion object {
        private const val POSITION_FIRST = 1
    }

    private val channelsPlaceNrTextView = itemView.itemChannelsPlaceNrTextView
    private val channelsNameTextView = itemView.itemChannelsNameTextView
    private val channelsMessagesNrTextView = itemView.itemChannelsMessagesNrTextView
    private val channelsRankImageView = itemView.itemChannelsRankImageView

    private lateinit var item: ChannelStatistics

    init {
        itemView.setOnClickListener { onChannelClickListener?.onChannelClick(item) }
    }

    fun bind(item: ChannelStatistics, filterOption: ChannelsFilterOption) {
        this.item = item
        showMessagesNrAccordingToSelectedFilterOption(item, filterOption)
        with(item) {
            channelsPlaceNrTextView.text = (currentPositionInList.toString() + '.')
            channelsNameTextView.text = channelName
            changeRankViewVisibility(currentPositionInList)
            changeMessagesNrTextColor(currentPositionInList)
        }
    }

    private fun showMessagesNrAccordingToSelectedFilterOption(item: ChannelStatistics, filterOption: ChannelsFilterOption) {
        when (filterOption) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> channelsMessagesNrTextView.text = item.messageCount.toString()
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> channelsMessagesNrTextView.text = item.mentionsCount.toString()
            ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE-> channelsMessagesNrTextView.text = item.myMessageCount.toString()
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
        fun onChannelClick(channelStatistics: ChannelStatistics)
    }
}