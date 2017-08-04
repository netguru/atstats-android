package co.netguru.android.socialslack.feature.users.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.user.model.UserStatistic

class UsersAdapter(private val onUserClickListener: OnUserClickListener) : RecyclerView.Adapter<UsersViewHolder>() {

    internal val usersList: MutableList<UserStatistic> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(parent, onUserClickListener)

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount() = usersList.size

    internal fun addUsers(usersList: List<UserStatistic>) {
        this.usersList.clear()
        this.usersList.addAll(usersList)
        notifyDataSetChanged()
    }

    interface OnUserClickListener {
        fun onUserClick(clickedItemPosition: Int)
    }
}