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
import co.netguru.android.atstats.feature.search.SearchActivity
import co.netguru.android.atstats.feature.share.ShareDialogFragment
import co.netguru.android.atstats.feature.shared.base.BaseMvpFragmentWithMenu
import kotlinx.android.synthetic.main.channel_statistics_cardview.*
import kotlinx.android.synthetic.main.fragment_channel_profile.*
import kotlinx.android.synthetic.main.profile_header_layout.*

class ChannelProfileFragment : BaseMvpFragmentWithMenu<ChannelProfileContract.View,
        ChannelProfileContract.Presenter>(), ChannelProfileContract.View {

    companion object {
        fun newInstance(channelStatistics: ChannelStatistics, mostActiveItemList: Array<ChannelStatistics>,
                        filterOption: ChannelsFilterOption): ChannelProfileFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_CHANNEL, channelStatistics)
            bundle.putParcelableArray(KEY_CHANNEL_MOST_ACTIVE_LIST, mostActiveItemList)
            bundle.putString(FILTER_OPTION, filterOption.name)

            val channelProfileFragment = ChannelProfileFragment()
            channelProfileFragment.arguments = bundle

            return channelProfileFragment
        }

        private const val KEY_CHANNEL = "key:channel"
        private const val KEY_CHANNEL_MOST_ACTIVE_LIST = "key:channel_list"
        private const val FILTER_OPTION = "key:filterOption"
    }

    private val component by lazy { App.getUserComponent(context).plusChannelProfileComponent() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_channel_profile)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.apply {
            val channelStatistics: ChannelStatistics = getParcelable(KEY_CHANNEL)
            val filterOption = ChannelsFilterOption.valueOf(getString(FILTER_OPTION))
            setUpFields(channelStatistics, filterOption)
            getPresenter().getChannelInfo(channelStatistics.channelId)
        }
        shareWithUserButton.setOnClickListener { presenter.onShareButtonClick() }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionSearch -> {
            presenter.searchButtonClicked()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun getMenuResource() = R.menu.menu_fragment_search

    override fun showChannelInfo(totalMessages: Int, totalHere: Int, totalMentions: Int) {
        totalMessagesTextView.text = totalMessages.toString()
        totalOfHereTextView.text = totalHere.toString()
        yourMentionsTextView.text = totalMentions.toString()
        secondTotalHereTextView.text = totalHere.toString()
    }

    override fun showShareDialogFragment() {
        val channelStatistics: ChannelStatistics = arguments.getParcelable(KEY_CHANNEL)
        val channelArray: Array<ChannelStatistics> = arguments.getParcelableArray(KEY_CHANNEL_MOST_ACTIVE_LIST)
                .filterIsInstance(ChannelStatistics::class.java).toTypedArray()
        val filterOption = ChannelsFilterOption.valueOf(arguments.getString(FILTER_OPTION))

        ShareDialogFragment.newInstance(channelStatistics, channelArray, filterOption).show(fragmentManager, ShareDialogFragment.TAG)
    }

    override fun showError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(channelCardView, errorMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun showSearchView() {
        context.startActivity<SearchActivity>()
    }

    override fun createPresenter(): ChannelProfileContract.Presenter {
        return component.getPresenter()
    }

    override fun showLoadingView() {
        progressBar.visibility = View.VISIBLE
        channelCardView.visibility = View.INVISIBLE
    }

    override fun hideLoadingView() {
        progressBar.visibility = View.INVISIBLE
        channelCardView.visibility = View.VISIBLE
    }

    private fun setUpFields(channelStatistics: ChannelStatistics, filterOption: ChannelsFilterOption) {
        messagesDetailTextView.text = resources.getString(R.string.total_messages)
        channelNameTextView.text = getString(R.string.channel_hashtag, channelStatistics.channelName)
        rankTextView.text = channelStatistics.currentPositionInList.toString()
        titleTextView.text = getProperTitleText(filterOption)
    }

    private fun getProperTitleText(filterOption: ChannelsFilterOption) = when (filterOption) {
        ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> getString(R.string.most_active_channel_filter)
        ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> getString(R.string.channel_we_are_most_active)
        ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> getString(R.string.channel_we_are_mentioned_the_most)
    }
}