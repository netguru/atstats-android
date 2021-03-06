package co.netguru.android.atstats.feature.users.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.extensions.startActivity
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.model.UserStatistic
import co.netguru.android.atstats.feature.search.SearchActivity
import co.netguru.android.atstats.feature.share.ShareDialogFragment
import co.netguru.android.atstats.feature.shared.base.BaseMvpFragmentWithMenu
import co.netguru.android.atstats.feature.users.profile.adapter.UsersProfileAdapter
import kotlinx.android.synthetic.main.fragment_users_profile.*

class UsersProfileFragment : BaseMvpFragmentWithMenu<UsersProfileContract.View, UsersProfileContract.Presenter>(),
        UsersProfileContract.View {

    companion object {
        fun newInstance(userStatisticsList: Array<UserStatistic>, currentUserPosition: Int,
                        selectedFilterOption: UsersFilterOption): UsersProfileFragment {
            val fragment = UsersProfileFragment()
            val bundle = Bundle()

            bundle.putParcelableArray(USER_STATISTICS_LIST_KEY, userStatisticsList)
            bundle.putInt(CURRENT_USER_POSITION_KEY, currentUserPosition)
            bundle.putString(SELECTED_FILTER_OPTION, selectedFilterOption.name)
            fragment.arguments = bundle

            return fragment
        }

        private const val USER_STATISTICS_LIST_KEY = "key:user_statistics_list"
        private const val CURRENT_USER_POSITION_KEY = "key:current_user_position"
        private const val SELECTED_FILTER_OPTION = "key:selected_filter_option"
    }

    private val adapter by lazy {
        UsersProfileAdapter(object : UsersProfileAdapter.OnUsersShareButtonClickListener {
            override fun onShareButtonClick(clickedUserPosition: Int, usersList: List<UserStatistic>) {
                presenter.onShareButtonClicked(clickedUserPosition, usersList)
            }
        })
    }

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
            val selectedFilterOption = UsersFilterOption.valueOf(getString(SELECTED_FILTER_OPTION))
            presenter.prepareView(userStatisticsList, getInt(CURRENT_USER_POSITION_KEY), selectedFilterOption)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionSearch -> {
            presenter.searchButtonClicked()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun getMenuResource() = R.menu.menu_fragment_search

    override fun createPresenter() = component.getPresenter()

    override fun initView(userStatisticsList: List<UserStatistic>, currentFilterOption: UsersFilterOption) {
        adapter.addUsersStatistics(userStatisticsList, currentFilterOption)
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

    override fun showShareView(clickedItem: UserStatistic, usersList: List<UserStatistic>) {
        val filterOption = UsersFilterOption.valueOf(arguments.getString(SELECTED_FILTER_OPTION))
        ShareDialogFragment.newInstance(clickedItem, usersList.toTypedArray(), filterOption).show(fragmentManager, ShareDialogFragment.TAG)
    }

    override fun showSearchView() {
        context.startActivity<SearchActivity>()
    }

    private fun initRecyclerView() {
        usersProfileRecyclerView.setHasFixedSize(true)
        usersProfileRecyclerView.adapter = adapter
    }
}