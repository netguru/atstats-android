package co.netguru.android.socialslack.feature.search.users

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.socialslack.data.user.model.UserStatistic

class SearchUsersAdapter(private val usersList: List<UserStatistic>)
    : RecyclerView.Adapter<SearchUsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchUsersViewHolder(parent)

    override fun onBindViewHolder(holder: SearchUsersViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount() = usersList.size
}