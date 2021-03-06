package co.netguru.android.atstats.feature.users.profile.adapter

import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.GlideApp
import co.netguru.android.atstats.common.extensions.getAttributeDrawable
import co.netguru.android.atstats.data.filter.model.Filter
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.filter.users.UsersMessagesNumberProvider
import co.netguru.android.atstats.data.user.model.UserStatistic
import co.netguru.android.atstats.data.user.profile.model.Presence
import co.netguru.android.atstats.feature.shared.base.BaseViewHolder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_users_profile.view.*
import kotlinx.android.synthetic.main.profile_header_layout.view.*

internal class UsersProfileViewHolder(parent: ViewGroup,
                                      private val onShareButtonClickListener: OnShareButtonClickListener)
    : BaseViewHolder<UserStatistic>(LayoutInflater.from(parent.context).inflate(R.layout.item_users_profile, parent, false)) {

    companion object {
        private const val SENT_RECVD_MSG_DIVIDER = " / "
    }

    private val titleTextView = itemView.titleTextView
    private val totalMessagesTextView = itemView.totalMessagesTextView
    private val messagesDetailTextView = itemView.messagesDetailTextView
    private val rankTextView = itemView.rankTextView
    private val userAvatar = itemView.userAvatar
    private val userFirstLastNameTextView = itemView.userFirstLastNameTextView
    private val usernameTextView = itemView.usernameTextView
    private val totalMsgTextView = itemView.totalMsgTextView
    private val sentRecvdTextView = itemView.sentRecvdMsgTextView
    private val msgStreakTextView = itemView.msgStreakTextView
    private val shareButton = itemView.shareSendButton

    init {
        shareButton.setOnClickListener { onShareButtonClickListener.onShareButtonClicked(adapterPosition) }
    }

    override fun bind(item: UserStatistic, filter: Filter) {
        val usersFilter = filter as UsersFilterOption
        setTextViewsAccordingToCurrentFilterOption(usersFilter)
        totalMessagesTextView.text = UsersMessagesNumberProvider.getProperMessagesNumber(usersFilter, item).toString()

        with(item) {
            userFirstLastNameTextView.text = name
            usernameTextView.text = (itemView.context.getString(R.string.username, username))
            totalMsgTextView.text = totalMessages.toString()
            sentRecvdTextView.text = (sentMessages.toString() + SENT_RECVD_MSG_DIVIDER + receivedMessages.toString())
            msgStreakTextView.text = currentDayStreak.toString()
            rankTextView.text = currentPositionInList.toString()
            userFirstLastNameTextView.isActivated = presence == Presence.ACTIVE


            GlideApp.with(itemView)
                    .load(avatarUrl)
                    .placeholder(itemView.context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                    .error(itemView.context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                    .transforms(arrayOf(CenterCrop(), RoundedCorners(itemView.resources.getDimension(R.dimen.item_user_avatar_radius).toInt())))
                    .into(userAvatar)
        }
    }

    private fun setTextViewsAccordingToCurrentFilterOption(usersFilterOption: UsersFilterOption) {
        when (usersFilterOption) {
            UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> setTextOnTextViews(R.string.messages_received, R.string.sent_messages)
            UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> setTextOnTextViews(R.string.messages_sent, R.string.received_messages)
            UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> setTextOnTextViews(R.string.conversations, R.string.total_messages)
        }
    }

    private fun setTextOnTextViews(@StringRes titleResId: Int, @StringRes messageDetailResId: Int) {
        titleTextView.setText(titleResId)
        messagesDetailTextView.setText(messageDetailResId)
    }

    internal interface OnShareButtonClickListener {
        fun onShareButtonClicked(itemPosition: Int)
    }
}