package co.netguru.android.socialslack.feature.channels

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsAdapter
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsViewHolder
import co.netguru.android.socialslack.feature.channels.profile.ChannelProfileFragment
import co.netguru.android.socialslack.feature.filter.FilterActivity
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_channels.*

class ChannelsFragment : MvpFragment<ChannelsContract.View, ChannelsContract.Presenter>(),
        ChannelsContract.View, ChannelsViewHolder.ChannelClickListener {

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private lateinit var component: ChannelsComponent
    private lateinit var adapter: ChannelsAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater?.inflate(R.layout.fragment_channels, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_channels_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        presenter.getChannelsFromServer()
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

    override fun showChannels(channelList: List<Channel>) {
        adapter.addChannels(channelList)
    }

    override fun showError() {
        Snackbar.make(channelsRecyclerView, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

    override fun showFilterView() {
        FilterActivity.startActivity(context, FilterObjectType.CHANNELS)
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

    override fun onChannelClick(channel: Channel) {
        fragmentManager
                .beginTransaction()
<<<<<<< HEAD
                .replace(R.id.fragmentChannelRootContainer, ChannelProfileFragment.newInstance(channel.id))
=======
                // TODO replace name for id
                .replace(R.id.fragmentChannelRootContainer, ChannelProfileFragment.newInstance(channel.name, channel.currentPositionInList))
>>>>>>> task/ADR-144
                .addToBackStack(ChannelProfileFragment.TAG)
                .commit()
    }

    private fun initComponent() {
        component = App.getApplicationComponent(context)
                .plusChannelsComponent()
    }
}