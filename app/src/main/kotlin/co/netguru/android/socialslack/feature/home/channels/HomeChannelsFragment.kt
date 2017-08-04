package co.netguru.android.socialslack.feature.home.channels

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import kotlinx.android.synthetic.main.fragment_home_channels.*

class HomeChannelsFragment : Fragment() {

    companion object {
        fun newInstance() = HomeChannelsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home_channels)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO 11.07.17 download data from API
        initRecyclerWithMockData(channelsRecycler1)
        initRecyclerWithMockData(channelsRecycler2)
        initRecyclerWithMockData(channelsRecycler3)
    }

    private fun initRecyclerWithMockData(recyclerView: RecyclerView) {
        val channel1 = ChannelStatistics("0", "#tradeguru-design", 420, 200,
                200, 200)
        val channel2 = ChannelStatistics("0", "#tradeguru-design", 420, 200,
                200, 200)
        val channel3 = ChannelStatistics("0", "#tradeguru-design", 420, 200,
                200, 200)
        val channelsAdapter = HomeChannelsAdapter()
        channelsAdapter.addUsers(listOf(channel1, channel2, channel3))

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = channelsAdapter
    }
}