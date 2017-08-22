package co.netguru.android.socialslack.feature.channels.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.getAttributeColor
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.channels.ChannelsMessagesNumberProvider
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.Filter
import co.netguru.android.socialslack.feature.shared.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_channels.view.*

class ChannelsViewHolder(parent: ViewGroup, private val onChannelClickListener: ChannelsAdapter.ChannelClickListener? = null)
    : BaseViewHolder<ChannelStatistics>(LayoutInflater.from(parent.context).inflate(R.layout.item_channels, parent, false)) {

    companion object {
        private const val POSITION_FIRST = 1
    }

    private val channelsPlaceNrTextView = itemView.itemChannelsPlaceNrTextView
    private val channelsNameTextView = itemView.itemChannelsNameTextView
    private val channelsMessagesNrTextView = itemView.itemChannelsMessagesNrTextView
    private val channelsRankImageView = itemView.itemChannelsRankImageView

    init {
        itemView.setOnClickListener { onChannelClickListener?.onChannelClick(adapterPosition) }
    }

    override fun bind(item: ChannelStatistics, filter: Filter) {
        val channelsFilter = filter as ChannelsFilterOption
        channelsMessagesNrTextView.text = ChannelsMessagesNumberProvider.getProperMessagesNumber(channelsFilter, item).toString()
        with(item) {
            channelsPlaceNrTextView.text = (currentPositionInList.toString() + '.')
            channelsNameTextView.text = channelName
            changeRankViewVisibility(currentPositionInList)
            changeMessagesNrTextColor(currentPositionInList)
        }
    }

    private fun changeRankViewVisibility(channelsPositionInList: Int) {
        channelsRankImageView.visibility = if (channelsPositionInList == POSITION_FIRST)
            View.VISIBLE else View.GONE
    }

    private fun changeMessagesNrTextColor(channelsPositionInList: Int) {
        val context = itemView.context
        channelsMessagesNrTextView.setTextColor(
                if (channelsPositionInList == POSITION_FIRST)
                    context.getAttributeColor(R.attr.colorHighlight) else context.getAttributeColor(R.attr.colorNormal))
    }
}