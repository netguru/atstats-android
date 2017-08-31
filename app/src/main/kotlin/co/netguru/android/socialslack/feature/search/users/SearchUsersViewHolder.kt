package co.netguru.android.socialslack.feature.search.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.user.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_users_search.view.*

class SearchUsersViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_users_search, parent, false)) {

    companion object {
        private const val FIRST_ADAPTER_POSITION = 0
    }

    private val userAvatarImageView = itemView.userAvatarImageView
    private val userRealNameTextView = itemView.userRealNameTextView
    private val userMedalImageView = itemView.userMedalImageView
    private val usernameTextView = itemView.usernameTextView

    fun bind(user: User) {
        with(user) {
            loadUserPhoto(profile.image512)
            userRealNameTextView.text = realName
            usernameTextView.text = (itemView.context.getString(R.string.username, username))
            changeMedalVisibility()
        }
    }

    private fun changeMedalVisibility() {
        userMedalImageView.visibility = if (adapterPosition == FIRST_ADAPTER_POSITION)
            View.VISIBLE else View.GONE
    }

    private fun loadUserPhoto(avatarUrl: String?) {
        Glide.with(itemView)
                // TODO 29.08.2017 find a better placeholder
                .load(avatarUrl ?: R.drawable.this_is_totally_a_person)
                .apply(RequestOptions.centerCropTransform()
                        .transform(RoundedCorners(itemView.resources.getDimension(R.dimen.item_user_avatar_radius).toInt())))
                .into(userAvatarImageView)
    }
}