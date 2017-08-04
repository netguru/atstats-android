package co.netguru.android.socialslack.feature.users.profile.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.user.model.UserStatistic

class UsersProfileAdapter : RecyclerView.Adapter<UsersProfileViewHolder>() {

    private val usersStatisticsList: MutableList<UserStatistic> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersProfileViewHolder(parent)

    override fun onBindViewHolder(holder: UsersProfileViewHolder, position: Int) {
        holder.bind(usersStatisticsList[position])
    }

    override fun getItemCount() = usersStatisticsList.size

    internal fun addUsersStatistics(usersStatisticsList: List<UserStatistic>) {
        this.usersStatisticsList.clear()
        this.usersStatisticsList.addAll(usersStatisticsList)
        notifyDataSetChanged()
    }
}