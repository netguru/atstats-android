package co.netguru.android.atstats.feature.channels.profile

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.extensions.inflate
import co.netguru.android.atstats.common.extensions.startActivity
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.shared.RandomMessageProvider
import co.netguru.android.atstats.feature.channels.profile.adapter.ChannelsProfileAdapter
import co.netguru.android.atstats.feature.search.SearchActivity
import co.netguru.android.atstats.feature.share.ShareDialogFragment
import co.netguru.android.atstats.feature.shared.base.BaseMvpFragmentWithMenu
import kotlinx.android.synthetic.main.fragment_channel_profile.*

class ChannelProfileFragment : BaseMvpFragmentWithMenu<ChannelProfileContract.View,
        ChannelProfileContract.Presenter>(), ChannelProfileContract.View {

    companion object {
        fun newInstance(channelsStatisticsArray: Array<ChannelStatistics>, currentPosition: Int,
                        filterOption: ChannelsFilterOption): ChannelProfileFragment {
            val channelProfileFragment = ChannelProfileFragment()
            val bundle = Bundle()

            bundle.putParcelableArray(KEY_CHANNEL_LIST, channelsStatisticsArray)
            bundle.putInt(KEY_CHANNEL_POSITION, currentPosition)
            bundle.putString(FILTER_OPTION, filterOption.name)
            channelProfileFragment.arguments = bundle

            return channelProfileFragment
        }

        private const val KEY_CHANNEL_LIST = "key:channel_list"
        private const val KEY_CHANNEL_POSITION = "key:current_channel_position"
        private const val FILTER_OPTION = "key:filterOption"
    }

    private val adapter by lazy {
        ChannelsProfileAdapter(object : ChannelsProfileAdapter.OnChannelsShareButtonClickListener {
            override fun onShareButtonClick(clickedChannelPosition: Int, channelList: List<ChannelStatistics>) {
                presenter.onShareButtonClick(clickedChannelPosition, channelList)
            }
        })
    }

    private val component by lazy { App.getUserComponent(context).plusChannelProfileComponent() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_channel_profile)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        with(arguments) {
            val channelsStatisticsList = getParcelableArray(KEY_CHANNEL_LIST)
                    .filterIsInstance(ChannelStatistics::class.java).toList()
            val filterOption = ChannelsFilterOption.valueOf(getString(FILTER_OPTION))
            getPresenter().prepareView(channelsStatisticsList, getInt(KEY_CHANNEL_POSITION), filterOption)
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

    override fun createPresenter(): ChannelProfileContract.Presenter {
        return component.getPresenter()
    }

    override fun initView(channelStatisticsList: List<ChannelStatistics>, filterOption: ChannelsFilterOption) {
        adapter.addChannelsStatistics(channelStatisticsList, filterOption)
    }

    override fun scrollToChannelPosition(position: Int) {
        channelsProfileRecyclerView.scrollToPosition(position)
    }

    override fun showLoadingView() {
        channelsProfileProgressBar.visibility = View.VISIBLE
        channelsProfileRecyclerView.visibility = View.GONE
    }

    override fun hideLoadingView() {
        channelsProfileProgressBar.visibility = View.GONE
        channelsProfileRecyclerView.visibility = View.VISIBLE
    }

    override fun showShareDialogFragment(clickedItem: ChannelStatistics, channelList: List<ChannelStatistics>) {
        val filterOption = ChannelsFilterOption.valueOf(arguments.getString(FILTER_OPTION))
        ShareDialogFragment.newInstance(clickedItem, channelList.toTypedArray(), filterOption).show(fragmentManager, ShareDialogFragment.TAG)
    }

    override fun showError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(channelsProfileRecyclerView, errorMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun showSearchView() {
        context.startActivity<SearchActivity>()
    }

    private fun initRecyclerView() {
        channelsProfileRecyclerView.setHasFixedSize(true)
        channelsProfileRecyclerView.adapter = adapter
    }
}