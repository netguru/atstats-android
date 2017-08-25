package co.netguru.android.socialslack.feature.home.channels

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import kotlinx.android.synthetic.main.item_channel.view.*

class HomeChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(channelStatistics: ChannelStatistics, filter: ChannelsFilterOption) {
        itemView.channelNameTextView.text = channelStatistics.channelName
        when (filter) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> {
                itemView.channelMessagesTextView.text = channelStatistics.messageCount.toString()
                itemView.channelMessagesTitleTextView.text = itemView.context.getText(R.string.messages)
            }
            ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> {
                itemView.channelMessagesTextView.text = channelStatistics.myMessageCount.toString()
                itemView.channelMessagesTitleTextView.text = itemView.context.getText(R.string.messages)
            }
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> {
                itemView.channelMessagesTextView.text = channelStatistics.mentionsCount.toString()
                itemView.channelMessagesTitleTextView.text = itemView.context.getText(R.string.mentions)
            }
        }
    }
}