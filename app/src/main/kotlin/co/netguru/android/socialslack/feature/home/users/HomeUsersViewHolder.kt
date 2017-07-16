package co.netguru.android.socialslack.feature.home.users

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.common.util.ViewUtils.roundImageView
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserStatistic
import kotlinx.android.synthetic.main.item_user.view.*

class HomeUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: UserStatistic) {
        // TODO 11.07.17 remove mock data
        itemView.userMessages.text = user.messages.toString()
        itemView.userName.text = user.name

        itemView.userAvatar.roundImageView()
    }
}