package co.netguru.android.socialslack.feature.home.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic

class HomeUsersAdapter(private val filterOption: UsersFilterOption) : RecyclerView.Adapter<HomeUsersViewHolder>() {

    private val users: MutableList<UserStatistic> = mutableListOf()

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: HomeUsersViewHolder, position: Int) {
        holder.bind(users[position], filterOption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeUsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return HomeUsersViewHolder(view)
    }

    fun addUsers(users: List<UserStatistic>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }
}