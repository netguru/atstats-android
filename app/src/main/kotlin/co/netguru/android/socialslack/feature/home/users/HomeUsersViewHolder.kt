package co.netguru.android.socialslack.feature.home.users

import android.view.View
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.shared.base.BaseViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class HomeUsersViewHolder(itemView: View) : BaseViewHolder<UserStatistic>(itemView) {

    override fun bind(item: UserStatistic) {
        with(item) {
            itemView.userMessages.text = messages.toString()
            itemView.userName.text = name
            Glide.with(itemView.context).load(avatarUrl).into(itemView.userAvatar)
        }
    }
}