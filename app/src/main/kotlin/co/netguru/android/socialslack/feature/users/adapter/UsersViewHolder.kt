package co.netguru.android.socialslack.feature.users.adapter

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.shared.base.BaseViewHolder
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_users.view.*

class UsersViewHolder(parent: ViewGroup, private val onUserClickListener: UsersAdapter.OnUserClickListener)
    : BaseViewHolder<UserStatistic>(LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)) {

    companion object {
        private const val POSITION_FIRST = 1
        private const val USERNAME_PREFIX = "@"
        private const val USER_AVATAR_ROUNDED_CORNERS_MARGIN = 0
    }

    private val placeNrTextView = itemView.placeNrTextView
    private val userAvatarImageView = itemView.userAvatarImageView
    private val userRealNameTextView = itemView.userRealNameTextView
    private val usernameTextView = itemView.usernameTextView
    private val messagesNrTextView = itemView.messagesNrTextView
    private val userMedalImageView = itemView.userMedalImageView

    init {
        itemView.setOnClickListener { onUserClickListener.onUserClick(adapterPosition) }
    }

    override fun bind(item: UserStatistic) {
        with(item) {
            placeNrTextView.text = (currentPositionInList.toString() + '.')
            userRealNameTextView.text = name
            usernameTextView.text = (USERNAME_PREFIX + username)
            //TODO 04.08.2017 Should be set according to selected filter option
            messagesNrTextView.text = totalMessages.toString()
            loadUserPhoto(avatarUrl)
            changeMessagesNrTextColor(currentPositionInList)
            changeMedalVisibility(currentPositionInList)
        }
    }

    private fun changeMessagesNrTextColor(channelsPositionInList: Int) {
        messagesNrTextView.setTextColor(ContextCompat.getColor(itemView.context,
                if (channelsPositionInList == POSITION_FIRST) R.color.orange else R.color.primary))
    }

    private fun changeMedalVisibility(channelsPositionInList: Int) {
        userMedalImageView.visibility = if (channelsPositionInList == POSITION_FIRST)
            View.VISIBLE else View.GONE
    }

    private fun loadUserPhoto(avatarUrl: String) {
        Glide.with(itemView.context)
                .load(avatarUrl)
                .bitmapTransform(RoundedCornersTransformation(itemView.context,
                        itemView.resources.getDimension(R.dimen.item_user_avatar_radius).toInt(),
                        USER_AVATAR_ROUNDED_CORNERS_MARGIN))
                .into(userAvatarImageView)
    }
}