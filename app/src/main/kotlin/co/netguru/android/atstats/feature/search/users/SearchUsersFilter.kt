package co.netguru.android.atstats.feature.search.users

import android.widget.Filter
import co.netguru.android.atstats.data.user.model.User
import java.lang.ref.WeakReference

class SearchUsersFilter(private val usersList: List<User>,
                        private val adapter: WeakReference<SearchUsersAdapter>) : Filter() {

    override fun performFiltering(constraint: CharSequence) = FilterResults().apply {
        val filteredList = usersList.filter { it.realName?.toLowerCase()?.contains(constraint.toString().toLowerCase()) ?: false }

        values = filteredList
        count = filteredList.size
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        val filteredList = (results.values as List<*>).filterIsInstance(User::class.java)
        adapter.get()?.addUsers(filteredList)
    }
}