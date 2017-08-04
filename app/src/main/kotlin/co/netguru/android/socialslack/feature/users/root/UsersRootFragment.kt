package co.netguru.android.socialslack.feature.users.root

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.feature.users.UsersFragment

class UsersRootFragment : Fragment() {

    companion object {
        fun newInstance() = UsersRootFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentUsersRootContainer, UsersFragment.newInstance())
                .commit()
    }
}