package co.netguru.android.atstats.feature.home.users

import android.view.View
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.GlideApp
import co.netguru.android.atstats.common.extensions.getAttributeDrawable
import co.netguru.android.atstats.data.filter.model.Filter
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.filter.users.UsersMessagesNumberProvider
import co.netguru.android.atstats.data.user.model.UserStatistic
import co.netguru.android.atstats.feature.shared.base.BaseViewHolder
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_users_home.view.*

class HomeUsersViewHolder(itemView: View) : BaseViewHolder<UserStatistic>(itemView) {

    private val userAvatarImageView = itemView.userAvatar
    private val usernameTextView = itemView.userName
    private val messagesNrTextView = itemView.userMessages

    override fun bind(item: UserStatistic, filter: Filter) {
        val usersFilter = filter as UsersFilterOption
        with(item) {
            messagesNrTextView.text = UsersMessagesNumberProvider.getProperMessagesNumber(usersFilter, item).toString()
            usernameTextView.text = name
            GlideApp.with(itemView)
                    .load(avatarUrl)
                    .placeholder(itemView.context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                    .error(itemView.context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                    .transforms(arrayOf(CenterInside(), RoundedCorners(itemView.resources.getDimension(R.dimen.item_user_avatar_radius).toInt())))
                    .into(userAvatarImageView)
        }
    }
}