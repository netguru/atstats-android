package co.netguru.android.socialslack.feature.users.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.shared.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_users_profile.view.*
import kotlinx.android.synthetic.main.profile_header_layout.view.*

class UsersProfileViewHolder(parent: ViewGroup) : BaseViewHolder<UserStatistic>(
        LayoutInflater.from(parent.context).inflate(R.layout.item_users_profile, parent, false)) {

    private val titleTextView = itemView.titleTextView
    private val totalMessagesTextView = itemView.totalMessagesTextView
    private val messagesDetailTextView = itemView.messagesDetailTextView
    private val rankTextView = itemView.rankTextView
    private val userFirstLastNameTextView = itemView.userFirstLastNameTextView
    private val usernameTextView = itemView.usernameTextView
    private val totalMsgTextView = itemView.totalMsgTextView
    private val sentRecvdTextView = itemView.sentRecvdMsgTextView
    private val msgStreakTextView = itemView.msgStreakTextView

    override fun bind(item: UserStatistic) {
        titleTextView.text = "Osoba ktora najwiecej pisze"
        rankTextView.text = "1"
        totalMessagesTextView.text = "100"
        messagesDetailTextView.text = "Received messages"
        userFirstLastNameTextView.text = "Anna Rice"
        usernameTextView.text = "/@anna.rice"
        totalMsgTextView.text = "340"
        sentRecvdTextView.text = "300 / 40"
        msgStreakTextView.text = "7"
    }
}