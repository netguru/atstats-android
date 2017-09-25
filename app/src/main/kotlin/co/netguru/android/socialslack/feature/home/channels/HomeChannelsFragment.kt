package co.netguru.android.socialslack.feature.home.channels

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.shared.RandomMessageProvider
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_home_channels.*

class HomeChannelsFragment : MvpFragment<HomeChannelsContract.View, HomeChannelsContract.Presenter>(), HomeChannelsContract.View {

    val component: HomeChannelsComponent by lazy { App.getUserComponent(context).plusHomeChannelsComponent() }

    override fun createPresenter(): HomeChannelsPresenter = component.getPresenter()

    companion object {
        fun newInstance() = HomeChannelsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home_channels)
    }

    override fun showMostActiveChannels(mostActiveChannelList: List<ChannelStatistics>,
                                        filter: ChannelsFilterOption) {
        initRecyclerView(mostActiveChannelsRecycler, mostActiveChannelList, filter)
    }

    override fun showChannelsWeAreMentionTheMost(weAreMentionMostChannelList: List<ChannelStatistics>,
                                                 filter: ChannelsFilterOption) {
        initRecyclerView(channelsWeAreMentionTheMostRecycler, weAreMentionMostChannelList, filter)
    }

    override fun showChannelsWeAreMostActive(weAreMostActiveChannelList: List<ChannelStatistics>,
                                             filter: ChannelsFilterOption) {
        initRecyclerView(channelsWeAreMostActiveRecycler, weAreMostActiveChannelList, filter)
    }

    override fun showErrorSortingChannels() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(mainLayout, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

    private fun initRecyclerView(recyclerView: RecyclerView,
                                 channelList: List<ChannelStatistics>,
                                 channelsFilterOption: ChannelsFilterOption) {
        val channelsAdapter = HomeChannelsAdapter(channelsFilterOption)
        channelsAdapter.addChannels(channelList)

        recyclerView.adapter = channelsAdapter
    }
}