package co.netguru.android.socialslack.feature.channels

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.feature.channels.adapter.ChannelsAdapter
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_channels.*

class ChannelsFragment : MvpFragment<ChannelsContract.View, ChannelsContract.Presenter>(),
        ChannelsContract.View {

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
        //TODO 13.07.2017 Show filter fragment
//        fragmentManager.beginTransaction()
//                .add(R.id., FilterFragment.newInstance())
//                .addToBackStack(null)
//                .commit()
    }

    override fun createPresenter(): ChannelsPresenter = component.getPresenter()

    private fun initRecyclerView() {
        adapter = ChannelsAdapter()
        channelsRecyclerView.setHasFixedSize(true)
        channelsRecyclerView.addItemDecoration(DividerItemDecorator(context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
        channelsRecyclerView.adapter = adapter
    }

    private fun initComponent() {
        component = App.getApplicationComponent(context)
                .plusChannelsComponent()
    }
}