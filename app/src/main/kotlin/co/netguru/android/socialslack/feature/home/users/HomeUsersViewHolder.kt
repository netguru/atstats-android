package co.netguru.android.socialslack.feature.home.users

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.user.User
import kotlinx.android.synthetic.main.item_user.view.*

class HomeUsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        // TODO 11.07.17 remove mock data
        itemView.userMessages.text = "32"
        itemView.userName.text = user.name
    }
}