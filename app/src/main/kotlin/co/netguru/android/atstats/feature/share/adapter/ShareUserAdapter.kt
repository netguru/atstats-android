package co.netguru.android.atstats.feature.share.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.model.UserStatistic
import co.netguru.android.atstats.feature.users.adapter.UsersViewHolder

class ShareUserAdapter(private val usersList: List<UserStatistic>, private val filterOption: UsersFilterOption)
    : RecyclerView.Adapter<UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(parent, R.layout.item_users)

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(usersList[position], filterOption)
    }

    override fun getItemCount() = usersList.size
}