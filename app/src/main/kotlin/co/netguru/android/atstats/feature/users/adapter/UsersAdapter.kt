package co.netguru.android.atstats.feature.users.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.model.UserStatistic

class UsersAdapter(private val onUserClickListener: OnUserClickListener) : RecyclerView.Adapter<UsersViewHolder>() {

    internal val usersList: MutableList<UserStatistic> = mutableListOf()

    internal lateinit var selectedFilterOption: UsersFilterOption

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(parent,
            R.layout.item_users, onUserClickListener)

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(usersList[position], selectedFilterOption)
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