package co.netguru.android.socialslack.feature.home.users

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class HomeUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: UserStatistic) {
        with(user) {
            itemView.userMessages.text = user.messages.toString()
            itemView.userName.text = user.name
            Glide.with(itemView.context).load(user.avatarUrl).into(itemView.userAvatar)
            // TODO round image view with glide instead of itemView.userAvatar.roundImageView()
        }
    }
}