package co.netguru.android.socialslack.feature.search.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_users.view.*

class SearchUsersViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_users_search, parent, false)) {

    companion object {
        private const val USER_AVATAR_ROUNDED_CORNERS_MARGIN = 0
    }

    private val userAvatarImageView = itemView.userAvatarImageView
    private val userRealNameTextView = itemView.userRealNameTextView
    private val usernameTextView = itemView.usernameTextView
    private val messagesNrTextView = itemView.messagesNrTextView

    fun bind(userStatistic: UserStatistic) {
        with(userStatistic) {
            loadUserPhoto(avatarUrl)
            userRealNameTextView.text = name
            usernameTextView.text = (itemView.context.getString(R.string.at) + username)
            messagesNrTextView.text = totalMessages.toString()
        }
    }

    private fun loadUserPhoto(avatarUrl: String?) {
        Glide.with(itemView.context)
                // TODO 29.08.2017 find a better placeholder
                .load(avatarUrl ?: R.drawable.this_is_totally_a_person)
                .bitmapTransform(RoundedCornersTransformation(itemView.context,
                        itemView.resources.getDimension(R.dimen.item_user_avatar_radius).toInt(),
                        USER_AVATAR_ROUNDED_CORNERS_MARGIN))
                .into(userAvatarImageView)
    }
}