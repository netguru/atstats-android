package co.netguru.android.socialslack.feature.home.users

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.user.User
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_home_users.*

class HomeUsersFragment : MvpFragment<HomeUsersContract.View, HomeUsersContract.Presenter>(),
        HomeUsersContract.View {

    private lateinit var component: HomeUsersComponent

    override fun createPresenter(): HomeUsersContract.Presenter {
        return component.getPresenter()
    }

    companion object {
        fun newInstance() = HomeUsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater?.inflate(R.layout.fragment_home_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerWithMockData(usersRecycler1)
        initRecyclerWithMockData(usersRecycler2)
        initRecyclerWithMockData(usersRecycler3)
    }

    fun initComponent() {
        component = App.getApplicationComponent(context).plusHomeUsersComponent()
    }

    // TODO 13.07.17 remove mock data when connected to API
    private fun initRecyclerWithMockData(recyclerView: RecyclerView) {
        val user1 = User("Ala Janosz")
        val user2 = User("Jyn Erso")
        val user3 = User("John Rambo")
        val usersAdapter = HomeUsersAdapter()
        usersAdapter.addUsers(listOf(user1, user2, user3))

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = usersAdapter
    }
}