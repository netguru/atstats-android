package co.netguru.android.socialslack.feature.users.profile.adapter

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.profile.Presence
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_users_profile.view.*
import kotlinx.android.synthetic.main.profile_header_layout.view.*

class UsersProfileViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_users_profile, parent, false)) {

    companion object {
        private const val USERNAME_PREFIX = "@"
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

    fun bind(item: UserStatistic, usersFilterOption: UsersFilterOption) {
        setTextViewsAccordingToCurrentFilterOption(usersFilterOption)

        with(item) {
            totalMessagesTextView.text = totalMessages.toString()
            userFirstLastNameTextView.text = name
            usernameTextView.text = (USERNAME_PREFIX + username)
            totalMsgTextView.text = totalMessages.toString()
            sentRecvdTextView.text = (sentMessages.toString() + SENT_RECVD_MSG_DIVIDER + receivedMessages.toString())
            msgStreakTextView.text = currentDayStreak.toString()
            rankTextView.text = currentPositionInList.toString()
            userFirstLastNameTextView.isActivated = presence == Presence.ACTIVE

            Glide.with(itemView.context).load(avatarUrl).into(userAvatar)
        }
    }

    private fun setTextViewsAccordingToCurrentFilterOption(usersFilterOption: UsersFilterOption) {
        when (usersFilterOption) {
            UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> setTextOnTextViews(R.string.person_we_write_most, R.string.sent_messages)
            UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> setTextOnTextViews(R.string.person_writes_to_us_most, R.string.received_messages)
            UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> setTextOnTextViews(R.string.person_we_talk_most, R.string.total_messages)
        }
    }

    private fun setTextOnTextViews(@StringRes titleResId: Int, @StringRes messageDetailResId: Int) {
        titleTextView.setText(titleResId)
        messagesDetailTextView.setText(messageDetailResId)
    }
}