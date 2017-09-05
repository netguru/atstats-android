package co.netguru.android.socialslack.feature.users.adapter

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.getAttributeColor
import co.netguru.android.socialslack.data.filter.model.Filter
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.filter.users.UsersMessagesNumberProvider
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.shared.base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_users.view.*

class UsersViewHolder(parent: ViewGroup, @LayoutRes private val layoutRes: Int,
                      private val onUserClickListener: UsersAdapter.OnUserClickListener? = null)
    : BaseViewHolder<UserStatistic>(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)) {

    companion object {
        private const val POSITION_FIRST = 1
    }

    private val placeNrTextView = itemView.placeNrTextView
    private val userAvatarImageView = itemView.userAvatarImageView
    private val userRealNameTextView = itemView.userRealNameTextView
    private val usernameTextView = itemView.usernameTextView
    private val messagesNrTextView = itemView.messagesNrTextView
    private val userMedalImageView = itemView.userMedalImageView

    init {
        itemView.setOnClickListener { onUserClickListener?.onUserClick(adapterPosition) }
    }

    override fun bind(item: UserStatistic, filter: Filter) {
        val usersFilter = filter as UsersFilterOption
        messagesNrTextView.text = UsersMessagesNumberProvider.getProperMessagesNumber(usersFilter, item).toString()
        with(item) {
            loadUserPhoto(avatarUrl)
            placeNrTextView.text = (currentPositionInList.toString() + '.')
            userRealNameTextView.text = name
            usernameTextView.text = (itemView.context.getString(R.string.username,username))
            changeMessagesNrTextColor(currentPositionInList)
            changeMedalVisibility(currentPositionInList)
        }
    }

    private fun changeMessagesNrTextColor(channelsPositionInList: Int) {
        val context = itemView.context
        messagesNrTextView.setTextColor(if (channelsPositionInList == POSITION_FIRST)
            context.getAttributeColor(R.attr.colorHighlight) else context.getAttributeColor(R.attr.colorNormal))
    }

    private fun changeMedalVisibility(channelsPositionInList: Int) {
        userMedalImageView.visibility = if (channelsPositionInList == POSITION_FIRST)
            View.VISIBLE else View.GONE
    }

    private fun loadUserPhoto(avatarUrl: String?) {
        Glide.with(itemView)
                // TODO 14.08.2017 find a better placeholder
                .load(avatarUrl ?: R.attr.userPlaceholderDrawable)
                .apply(RequestOptions.centerCropTransform()
                        .transform(RoundedCorners(itemView.resources.getDimension(R.dimen.item_user_avatar_radius).toInt())))
                .into(userAvatarImageView)
    }
}