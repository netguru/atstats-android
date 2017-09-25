package co.netguru.android.atstats.feature.home.channels

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.atstats.R
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import kotlinx.android.synthetic.main.item_channel_home.view.*

class HomeChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(channelStatistics: ChannelStatistics, filter: ChannelsFilterOption) {
        itemView.channelNameTextView.text = itemView.context.getString(R.string.channel_hashtag, channelStatistics.channelName)
        itemView.channelMessagesTitleTextView.text = itemView.context.getText(R.string.messages)
        when (filter) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> {
                itemView.channelMessagesTextView.text = channelStatistics.messageCount.toString()
            }
            ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> {
                itemView.channelMessagesTextView.text = channelStatistics.myMessageCount.toString()
            }
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> {
                itemView.channelMessagesTitleTextView.text = itemView.context.getText(R.string.mentions)
                itemView.channelMessagesTextView.text = channelStatistics.mentionsCount.toString()
            }
        }
    }
}