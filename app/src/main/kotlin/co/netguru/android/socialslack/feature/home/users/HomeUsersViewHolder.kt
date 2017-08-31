package co.netguru.android.socialslack.feature.home.users

import android.view.View
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.filter.model.Filter
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.filter.users.UsersMessagesNumberProvider
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.shared.base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

class HomeUsersViewHolder(itemView: View) : BaseViewHolder<UserStatistic>(itemView) {

    override fun bind(item: UserStatistic, filter: Filter) {
        val usersFilter = filter as UsersFilterOption
        with(item) {
            itemView.userMessages.text = UsersMessagesNumberProvider.getProperMessagesNumber(usersFilter, item).toString()
            itemView.userName.text = name
            Glide.with(itemView)
                    .load(avatarUrl)
                    .apply(RequestOptions.centerCropTransform()
                            .transform(RoundedCorners(itemView.resources.getDimension(R.dimen.item_user_avatar_radius).toInt())))
                    .into(itemView.userAvatar)
        }
    }
}