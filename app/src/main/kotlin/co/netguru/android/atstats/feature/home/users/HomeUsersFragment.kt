package co.netguru.android.atstats.feature.home.users

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.extensions.inflate
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_home_users.*

internal class HomeUsersFragment : MvpFragment<HomeUsersContract.View, HomeUsersContract.Presenter>(),
        HomeUsersContract.View {

    private val component by lazy { App.getUserComponent(context).plusHomeUsersComponent() }

    override fun createPresenter(): HomeUsersContract.Presenter {
        return component.getPresenter()
    }

    companion object {
        fun newInstance() = HomeUsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home_users)
    }

    override fun setUsersWeWriteMost(users: List<UserStatistic>) {
        initRecyclerWithData(usersRecycler1, users, UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST)
    }

    override fun setUsersThatWriteToUsTheMost(users: List<UserStatistic>) {
        initRecyclerWithData(usersRecycler2, users, UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST)
    }

    override fun setUsersWeTalkTheMost(users: List<UserStatistic>) {
        initRecyclerWithData(usersRecycler3, users, UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST)
    }

    private fun initRecyclerWithData(recyclerView: RecyclerView, userStatisticList: List<UserStatistic>,
                                     filterOption: UsersFilterOption) {
        val usersAdapter = HomeUsersAdapter(filterOption)
        usersAdapter.addUsers(userStatisticList)
        recyclerView.adapter = usersAdapter
    }
}