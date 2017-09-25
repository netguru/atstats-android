package co.netguru.android.atstats.feature.search

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.extensions.inflate
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.shared.RandomMessageProvider
import co.netguru.android.atstats.data.user.model.User
import co.netguru.android.atstats.feature.search.adapter.SearchItemType
import co.netguru.android.atstats.feature.search.channels.SearchChannelsAdapter
import co.netguru.android.atstats.feature.search.users.SearchUsersAdapter
import co.netguru.android.atstats.feature.shared.view.DividerItemDecorator
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : MvpFragment<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {

    companion object {
        fun newInstance(searchItemType: SearchItemType): SearchFragment {
            val bundle = Bundle()
            bundle.putString(SEARCH_ITEM_TYPE, searchItemType.name)

            val searchFragment = SearchFragment()
            searchFragment.arguments = bundle

            return searchFragment
        }

        private const val SEARCH_ITEM_TYPE = "key:search_item_type"
    }

    private val component by lazy {
        App.getUserComponent(context).plusSearchComponent()
    }

    private val channelsAdapter by lazy { SearchChannelsAdapter() }
    private val usersAdapter by lazy { SearchUsersAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = container?.inflate(R.layout.fragment_search)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun createPresenter() = component.getPresenter()

    override fun initChannelSearchView(channelsList: List<ChannelStatistics>) {
        searchRecyclerView.adapter = channelsAdapter
        channelsAdapter.addChannels(channelsList)
    }

    override fun initUsersSearchView(usersList: List<User>) {
        searchRecyclerView.adapter = usersAdapter
        usersAdapter.addUsers(usersList)
    }

    override fun filterChannelsList(query: String) {
        channelsAdapter.filterChannels(query)
    }

    override fun filterUsersList(query: String) {
        usersAdapter.filterUsers(query)
    }

    override fun showProgressBar() {
        searchRecyclerView.visibility = View.GONE
        searchLoadingView.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        searchRecyclerView.visibility = View.VISIBLE
        searchLoadingView.visibility = View.GONE
    }

    override fun showError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(searchRecyclerView, errorMsg, Snackbar.LENGTH_LONG).show()
    }

    fun refreshData(query: String) {
        presenter.searchQueryReceived(query)
    }

    private fun initRecyclerView() {
        searchRecyclerView.setHasFixedSize(true)
        searchRecyclerView.addItemDecoration(DividerItemDecorator(
                context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
        presenter.searchItemReceived(SearchItemType.valueOf(arguments.getString(SEARCH_ITEM_TYPE)))
    }
}