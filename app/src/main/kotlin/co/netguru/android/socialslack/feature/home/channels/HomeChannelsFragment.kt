package co.netguru.android.socialslack.feature.home.channels

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
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

    override fun showMostActiveChannels(mostActiveChannelList: List<ChannelStatistics>) {
        initRecyclerView(channelsRecycler1, mostActiveChannelList)
    }

    override fun showChannelsWeAreMentionTheMost(weAreMentionMostChannelList: List<ChannelStatistics>) {
        initRecyclerView(channelsRecycler2, weAreMentionMostChannelList)
    }

    override fun showChannelsWeAreMostActive(weAreMostActiveChannelList: List<ChannelStatistics>) {
        initRecyclerView(channelsRecycler3, weAreMostActiveChannelList)
    }

    override fun showErrorSortingChannels() {
        Snackbar.make(mainLayout, R.string.error_sorting_channels, Snackbar.LENGTH_SHORT).show()
    }

    private fun initRecyclerView(recyclerView: RecyclerView, channelList: List<ChannelStatistics>) {
        val channelsAdapter = HomeChannelsAdapter()
        channelsAdapter.addUsers(channelList)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = channelsAdapter
    }
}