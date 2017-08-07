package co.netguru.android.socialslack.feature.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import co.netguru.android.socialslack.feature.users.adapter.UsersAdapter
import co.netguru.android.socialslack.feature.users.profile.UsersProfileFragment
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.filter_view.*
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : MvpFragment<UsersContract.View, UsersContract.Presenter>(), UsersContract.View {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private val adapter by lazy {
        UsersAdapter(object : UsersAdapter.OnUserClickListener {
            override fun onUserClick(clickedItemPosition: Int) {
                presenter.onUserClicked(clickedItemPosition)
            }
        })
    }

    private val component by lazy {
        App.getUserComponent(context).plusUsersComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        filterViewIconImageView.setImageResource(R.drawable.users_title_bar)
        //TODO 04.08.2017 Should be set according to selected filter option
        filterViewTextView.setText(R.string.person_we_talk_most)
        presenter.getUsersData()
    }

    override fun showUsersList(usersList: List<UserStatistic>) {
        adapter.addUsers(usersList)
    }

    override fun showLoadingView() {
        usersRecyclerView.visibility = View.GONE
        usersLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        usersRecyclerView.visibility = View.VISIBLE
        usersLoadingView.visibility = View.GONE
    }

    override fun showUserDetails(clickedUserPosition: Int) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentUsersRootContainer,
                        UsersProfileFragment.newInstance(adapter.usersList.toTypedArray(), clickedUserPosition))
                .addToBackStack(null)
                .commit()
    }

    override fun createPresenter() = component.getPresenter()

    private fun initRecyclerView() {
        usersRecyclerView.setHasFixedSize(true)
        usersRecyclerView.addItemDecoration(DividerItemDecorator(context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
        usersRecyclerView.adapter = adapter
    }
}