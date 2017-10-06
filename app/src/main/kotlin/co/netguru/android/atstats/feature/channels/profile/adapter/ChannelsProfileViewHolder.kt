package co.netguru.android.atstats.feature.channels.profile.adapter

import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.filter.model.Filter
import co.netguru.android.atstats.feature.shared.base.BaseViewHolder
import kotlinx.android.synthetic.main.channel_statistics_cardview.view.*
import kotlinx.android.synthetic.main.profile_header_layout.view.*


internal class ChannelsProfileViewHolder(parent: ViewGroup,
                                         private val onShareButtonClickListener: ChannelsProfileViewHolder.OnShareButtonClickListener)
    : BaseViewHolder<ChannelStatistics>(LayoutInflater.from(parent.context).inflate(R.layout.item_channels_profile, parent, false)) {

    private val messageDetailsTextView = itemView.messagesDetailTextView
    private val channelNameTextView = itemView.channelNameTextView
    private val rankTextView  = itemView.rankTextView
    private val titleTextView = itemView.titleTextView
    private val totalMessageTextView  = itemView.totalMessagesTextView
    private val sendMessagesTextView  = itemView.totalSendMessagesTextView
    private val yourMentionsTextView  = itemView.yourMentionsTextView
    private val totalOfHereTextView  = itemView.totalHereTextView
    private val shareButton = itemView.shareWithUserButton

    init {
        shareButton.setOnClickListener { onShareButtonClickListener.onShareButtonClicked(adapterPosition) }
    }

    override fun bind(item: ChannelStatistics, filter: Filter) {
        val channelsFilter = filter as ChannelsFilterOption
        messageDetailsTextView.setText(R.string.total_messages)
        setTextViewsAccordingToCurrentFilterOption(channelsFilter)

        with(item) {
            channelNameTextView.text = channelName
            rankTextView.text = currentPositionInList.toString()
            totalMessageTextView.text = messageCount.toString()
            sendMessagesTextView.text = myMessageCount.toString()
            yourMentionsTextView.text = mentionsCount.toString()
            totalOfHereTextView.text = hereCount.toString()
        }
    }

    private fun setTextViewsAccordingToCurrentFilterOption(channelsFilterOption: ChannelsFilterOption) {
        when (channelsFilterOption) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> setTextOnTextViews(R.string.most_active_channel_filter)
            ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> setTextOnTextViews(R.string.channel_we_are_most_active)
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> setTextOnTextViews(R.string.channel_we_are_mentioned_the_most)
        }
    }

    private fun setTextOnTextViews(@StringRes titleResId: Int) {
        titleTextView.setText(titleResId)
    }

    internal interface OnShareButtonClickListener {
        fun onShareButtonClicked(itemPosition: Int)
    }
}