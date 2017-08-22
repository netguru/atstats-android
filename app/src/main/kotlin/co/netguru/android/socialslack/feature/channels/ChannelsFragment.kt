package co.netguru.android.socialslack.feature.channels

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.getAttributeDrawable
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsAdapter
import co.netguru.android.socialslack.feature.channels.profile.ChannelProfileFragment
import co.netguru.android.socialslack.feature.filter.FilterActivity
import co.netguru.android.socialslack.feature.shared.base.BaseFragmentWithNestedFragment
import co.netguru.android.socialslack.feature.shared.base.BaseMvpFragmentWithMenu
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import kotlinx.android.synthetic.main.filter_view.*
import kotlinx.android.synthetic.main.fragment_channels.*

class ChannelsFragment : BaseMvpFragmentWithMenu<ChannelsContract.View, ChannelsContract.Presenter>(),
        ChannelsContract.View, ChannelsAdapter.ChannelClickListener {

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private val component by lazy { App.getUserComponent(context).plusChannelsComponent() }

    private lateinit var adapter: ChannelsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_channels)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        filterViewIconImageView.setImageResource(context.getAttributeDrawable(R.attr.hashtagTitleDrawable))
        presenter.getChannels()
        presenter.getCurrentFilterOption()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionFilter -> {
                presenter.filterButtonClicked()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun getMenuResource() = R.menu.menu_fragment_search_filter

    override fun showChannels(channelList: List<ChannelStatistics>) {
        adapter.addChannels(channelList)
    }

    override fun showError() {
        Snackbar.make(channelsRecyclerView, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

    override fun showFilterOptionError() {
        Snackbar.make(channelsRecyclerView, R.string.error_filter_option, Snackbar.LENGTH_LONG).show()
    }

    override fun setCurrentFilterOption(filterOption: ChannelsFilterOption) {
        filterViewTextView.setText(filterOption.textResId)
        adapter.selectedFilterOption = filterOption
    }

    override fun showFilterView() {
        FilterActivity.startActivity(context, FilterObjectType.CHANNELS)
    }

    override fun showLoadingView() {
        channelsRecyclerView.visibility = View.GONE
        channelLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        channelsRecyclerView.visibility = View.VISIBLE
        channelLoadingView.visibility = View.GONE
    }

    override fun showChannelDetails(channelStatistics: ChannelStatistics, channelList: List<ChannelStatistics>, filterOption: ChannelsFilterOption) {
        if (parentFragment is BaseFragmentWithNestedFragment) {
            val fragmentWithNestedFragment = parentFragment as BaseFragmentWithNestedFragment
            fragmentWithNestedFragment.replaceNestedFragmentAndAddToBackStack(R.id.fragmentChannelRootContainer,
                    ChannelProfileFragment.newInstance(channelStatistics, channelList.toTypedArray(), filterOption))
        } else {
            throw IllegalStateException("Parent fragment should be instance of BaseFragmentWithNestedFragment")
        }
    }

    override fun onChannelClick(clickedItemPosition: Int) {
        presenter.onChannelClick(clickedItemPosition, adapter.channelsList)
    }

    override fun createPresenter(): ChannelsPresenter = component.getPresenter()

    fun sortData() {
        presenter.sortRequestReceived(adapter.channelsList)
    }

    private fun initRecyclerView() {
        adapter = ChannelsAdapter(this)
        channelsRecyclerView.setHasFixedSize(true)
        channelsRecyclerView.addItemDecoration(DividerItemDecorator(context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
        channelsRecyclerView.adapter = adapter
    }
}