package co.netguru.android.socialslack.feature.users.profile

import android.os.Bundle
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
        fun newInstance(userStatisticsList: Array<UserStatistic>, currentUserPosition: Int): UsersProfileFragment {
            val fragment = UsersProfileFragment()
            val bundle = Bundle()

            bundle.putParcelableArray(USER_STATISTICS_LIST_KEY, userStatisticsList)
            bundle.putInt(CURRENT_USER_POSITION_KEY, currentUserPosition)
            fragment.arguments = bundle

            return fragment
        }

        private const val USER_STATISTICS_LIST_KEY = "key:user_statistics_list"
        private const val CURRENT_USER_POSITION_KEY = "key:current_user_position"
    }

    private val adapter by lazy { UsersProfileAdapter() }

    private val component by lazy {
        App.getUserComponent(context).plusUsersProfileComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        with(arguments) {
            val userStatisticsList = getParcelableArray(USER_STATISTICS_LIST_KEY)
                    .filterIsInstance(UserStatistic::class.java).toList()
            presenter.prepareView(userStatisticsList, getInt(CURRENT_USER_POSITION_KEY))
        }
    }

    override fun createPresenter() = component.getPresenter()

    override fun initView(userStatisticsList: List<UserStatistic>) {
        adapter.addUsersStatistics(userStatisticsList)
    }

    override fun scrollToUserPosition(userPosition: Int) {
        usersProfileRecyclerView.scrollToPosition(userPosition)
    }

    override fun showLoadingView() {
        usersProfileRecyclerView.visibility = View.GONE
        usersProfileProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        usersProfileRecyclerView.visibility = View.VISIBLE
        usersProfileProgressBar.visibility = View.GONE
    }

    private fun initRecyclerView() {
        usersProfileRecyclerView.setHasFixedSize(true)
        usersProfileRecyclerView.adapter = adapter
    }
}