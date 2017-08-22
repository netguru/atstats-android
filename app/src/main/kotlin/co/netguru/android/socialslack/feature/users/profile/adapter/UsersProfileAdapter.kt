package co.netguru.android.socialslack.feature.users.profile.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic

internal class UsersProfileAdapter(private val onUsersShareButtonClickListener: OnUsersShareButtonClickListener)
    : RecyclerView.Adapter<UsersProfileViewHolder>(), UsersProfileViewHolder.OnShareButtonClickListener {

    private val usersStatisticsList: MutableList<UserStatistic> = mutableListOf()

    private lateinit var selectedFilterOption: UsersFilterOption

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersProfileViewHolder(parent, this)

    override fun onBindViewHolder(holder: UsersProfileViewHolder, position: Int) {
        holder.bind(usersStatisticsList[position], selectedFilterOption)
    }

    override fun getItemCount() = usersStatisticsList.size

    override fun onShareButtonClicked(itemPosition: Int) {
        onUsersShareButtonClickListener.onShareButtonClick(itemPosition, usersStatisticsList)
    }

    internal fun addUsersStatistics(usersStatisticsList: List<UserStatistic>, usersFilterOption: UsersFilterOption) {
        this.selectedFilterOption = usersFilterOption
        this.usersStatisticsList.clear()
        this.usersStatisticsList.addAll(usersStatisticsList)
        notifyDataSetChanged()
    }

    interface OnUsersShareButtonClickListener {
        fun onShareButtonClick(clickedUserPosition: Int, usersList: List<UserStatistic>)
    }
}