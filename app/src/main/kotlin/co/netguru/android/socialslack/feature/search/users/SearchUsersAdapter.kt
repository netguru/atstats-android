package co.netguru.android.socialslack.feature.search.users

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.user.model.User

class SearchUsersAdapter : RecyclerView.Adapter<SearchUsersViewHolder>() {

    private val usersList = mutableListOf<User>()
    private val usersFilter by lazy {
        SearchUsersFilter(usersList.toList(), this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchUsersViewHolder(parent)

    override fun onBindViewHolder(holder: SearchUsersViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount() = usersList.size

    internal fun addUsers(usersList: List<User>) {
        this.usersList.clear()
        this.usersList.addAll(usersList)
        notifyDataSetChanged()
    }

    internal fun filterUsers(query: String) {
        usersFilter.filter(query)
    }
}