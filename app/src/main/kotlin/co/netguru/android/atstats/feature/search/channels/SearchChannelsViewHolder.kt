package co.netguru.android.atstats.feature.search.channels

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.common.extensions.getAttributeColor
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import kotlinx.android.synthetic.main.item_channels_search.view.*

class SearchChannelsViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_channels_search, parent, false)) {

    companion object {
        private const val FIRST_ADAPTER_POSITION = 0
    }

    private val channelsNameTextView = itemView.itemChannelsSearchNameTextView
    private val channelsMessagesNrTextView = itemView.itemChannelsSearchMessagesNrTextView

    fun bind(channelStatistics: ChannelStatistics) {
        with(channelStatistics) {
            channelsNameTextView.text = channelName
            channelsMessagesNrTextView.text = messageCount.toString()
            changeMessagesNrTextColor()
        }
    }

    private fun changeMessagesNrTextColor() {
        val context = itemView.context
        channelsMessagesNrTextView.setTextColor(
                if (adapterPosition == FIRST_ADAPTER_POSITION)
                    context.getAttributeColor(R.attr.colorHighlight) else context.getAttributeColor(R.attr.colorNormal))
    }
}