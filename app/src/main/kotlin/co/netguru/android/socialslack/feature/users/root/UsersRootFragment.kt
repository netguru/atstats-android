package co.netguru.android.socialslack.feature.users.root

import android.os.Bundle
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.feature.shared.base.BaseFragmentWithNestedFragment
import co.netguru.android.socialslack.feature.users.UsersFragment

class UsersRootFragment : BaseFragmentWithNestedFragment() {

    companion object {
        fun newInstance() = UsersRootFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replaceNestedFragment(R.id.fragmentUsersRootContainer, UsersFragment.newInstance())
    }

    fun sortUsersData() {
        val fragment = childFragmentManager.findFragmentById(R.id.fragmentUsersRootContainer)
        if (fragment is UsersFragment) {
            fragment.sortData()
        }
    }
}