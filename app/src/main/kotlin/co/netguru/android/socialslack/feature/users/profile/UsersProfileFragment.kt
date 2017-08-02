package co.netguru.android.socialslack.feature.users.profile

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.users.profile.adapter.UsersProfileAdapter
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_users_profile.*

class UsersProfileFragment : MvpFragment<UsersProfileContract.View, UsersProfileContract.Presenter>(),
        UsersProfileContract.View {

    companion object {
        fun newInstance() = UsersProfileFragment()
    }

    private val adapter by lazy { UsersProfileAdapter() }

    private val component by lazy {
        App.getApplicationComponent(context)
                .plusUsersProfileComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun createPresenter() = component.getPresenter()

    private fun initRecyclerView() {
        usersProfileRecyclerView.setHasFixedSize(true)
        usersProfileRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        usersProfileRecyclerView.adapter = adapter

        val list = mutableListOf<UserStatistic>()
        for (i in 0..10) {
           // list += UserStatistic("", 10, "")
        }
        adapter.addUsersStatistics(list)
    }
}