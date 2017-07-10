package co.netguru.android.socialslack.feature.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsAdapter
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_channels.*

class ChannelsFragment : MvpFragment<ChannelsContract.View, ChannelsContract.Presenter>(),
        ChannelsContract.View {

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private lateinit var component: ChannelsComponent
    private lateinit var adapter: ChannelsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater?.inflate(R.layout.fragment_channels, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun createPresenter(): ChannelsPresenter = component.getPresenter()

    private fun initRecyclerView() {
        adapter = ChannelsAdapter()
        channelsRecyclerView.setHasFixedSize(true)
        channelsRecyclerView.adapter = adapter
    }

    private fun initComponent() {
        component = App.getApplicationComponent(context)
                .plusChannelsComponent()
    }
}