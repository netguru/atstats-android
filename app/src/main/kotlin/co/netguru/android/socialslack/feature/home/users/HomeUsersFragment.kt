package co.netguru.android.socialslack.feature.home.users

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_home_users.*

class HomeUsersFragment : MvpFragment<HomeUsersContract.View, HomeUsersContract.Presenter>(),
        HomeUsersContract.View {

    private val component by lazy { App.getApplicationComponent(context).plusHomeUsersComponent() }

    override fun createPresenter(): HomeUsersContract.Presenter {
        return component.getPresenter()
    }

    companion object {
        fun newInstance() = HomeUsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUsersWeWriteMost(users: List<UserStatistic>) {
        initRecyclerWithData(usersRecycler1, users)
    }

    override fun setUsersThatWriteToUsTheMost(users: List<UserStatistic>) {
        initRecyclerWithData(usersRecycler2, users)
    }

    override fun setUsersWeTalkTheMost(users: List<UserStatistic>) {
        initRecyclerWithData(usersRecycler3, users)
    }

    private fun initRecyclerWithData(recyclerView: RecyclerView, userStatisticList: List<UserStatistic>) {
        val usersAdapter = HomeUsersAdapter()
        usersAdapter.addUsers(userStatisticList)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = usersAdapter
    }
}