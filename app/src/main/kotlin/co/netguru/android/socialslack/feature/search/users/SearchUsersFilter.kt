package co.netguru.android.socialslack.feature.search.users

import android.widget.Filter
import co.netguru.android.socialslack.data.user.model.User

class SearchUsersFilter(private val usersList: List<User>,
                        private val adapter: SearchUsersAdapter) : Filter() {

    private val filteredUsersList = mutableListOf<User>()

    override fun performFiltering(constraint: CharSequence): FilterResults {
        filteredUsersList.clear()
        val filterResults = FilterResults()

        usersList.filter { it.realName?.toLowerCase()?.contains(constraint.toString().toLowerCase()) ?: false }
                .forEach { filteredUsersList.add(it) }

        filterResults.values = filteredUsersList
        filterResults.count = filteredUsersList.size

        return filterResults
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        adapter.addUsers(filteredUsersList)
    }
}