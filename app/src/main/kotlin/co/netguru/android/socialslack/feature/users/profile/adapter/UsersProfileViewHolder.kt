package co.netguru.android.socialslack.feature.users.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.profile.Presence
import co.netguru.android.socialslack.feature.shared.base.BaseViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_users_profile.view.*
import kotlinx.android.synthetic.main.profile_header_layout.view.*

class UsersProfileViewHolder(parent: ViewGroup) : BaseViewHolder<UserStatistic>(
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

    override fun bind(item: UserStatistic) {
        //TODO 02.08.2017 title text should be set according to current filter option
        titleTextView.text = itemView.resources.getText(R.string.person_we_write_most)
        messagesDetailTextView.text = itemView.resources.getText(R.string.received_messages)

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
}