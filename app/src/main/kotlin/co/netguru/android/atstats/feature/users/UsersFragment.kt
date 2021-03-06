package co.netguru.android.atstats.feature.users

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.*
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.extensions.getAttributeDrawable
import co.netguru.android.atstats.common.extensions.startActivity
import co.netguru.android.atstats.data.filter.model.FilterObjectType
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.shared.RandomMessageProvider
import co.netguru.android.atstats.data.user.model.UserStatistic
import co.netguru.android.atstats.feature.filter.FilterActivity
import co.netguru.android.atstats.feature.search.SearchActivity
import co.netguru.android.atstats.feature.shared.base.BaseFragmentWithNestedFragment
import co.netguru.android.atstats.feature.shared.base.BaseMvpFragmentWithMenu
import co.netguru.android.atstats.feature.shared.view.DividerItemDecorator
import co.netguru.android.atstats.feature.users.adapter.UsersAdapter
import co.netguru.android.atstats.feature.users.profile.UsersProfileFragment
import kotlinx.android.synthetic.main.filter_view.*
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : BaseMvpFragmentWithMenu<UsersContract.View, UsersContract.Presenter>(), UsersContract.View {

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
        filterViewIconImageView.setImageResource(context.getAttributeDrawable(R.attr.usersTitleBarDrawable))
        presenter.getCurrentFilterOption()
        presenter.getUsersData()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionFilter -> {
            presenter.filterButtonClicked()
            true
        }
        R.id.actionSearch -> {
            presenter.searchButtonClicked()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun getMenuResource() = R.menu.menu_fragment_search_filter

    override fun showUsersList(usersList: List<UserStatistic>) {
        adapter.addUsers(usersList)
    }

    override fun showError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(usersRecyclerView, errorMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun showLoadingView() {
        usersRecyclerView.visibility = View.GONE
        usersLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        usersRecyclerView.visibility = View.VISIBLE
        usersLoadingView.visibility = View.GONE
    }

    override fun showUserDetails(clickedUserPosition: Int, selectedFilterOption: UsersFilterOption) {
        if (parentFragment is BaseFragmentWithNestedFragment) {
            val fragmentWithNestedFragment = parentFragment as BaseFragmentWithNestedFragment
            fragmentWithNestedFragment.replaceNestedFragmentAndAddToBackStack(R.id.fragmentUsersRootContainer,
                    UsersProfileFragment.newInstance(adapter.usersList.toTypedArray(), clickedUserPosition, selectedFilterOption))
        } else {
            throw IllegalStateException("Parent fragment should be instance of BaseFragmentWithNestedFragment")
        }
    }

    override fun showFilterView() {
        FilterActivity.startActivity(context, FilterObjectType.USERS)
    }

    override fun showSearchView() {
        context.startActivity<SearchActivity>()
    }

    override fun showFilterOptionError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(usersRecyclerView, errorMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun changeSelectedFilterOption(selectedFilterOption: UsersFilterOption) {
        filterViewTextView.setText(selectedFilterOption.textResId)
        adapter.selectedFilterOption = selectedFilterOption
    }

    override fun createPresenter() = component.getPresenter()

    fun sortData() {
        presenter.sortRequestReceived()
    }

    private fun initRecyclerView() {
        usersRecyclerView.setHasFixedSize(true)
        usersRecyclerView.addItemDecoration(DividerItemDecorator(context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
        usersRecyclerView.adapter = adapter
    }
}