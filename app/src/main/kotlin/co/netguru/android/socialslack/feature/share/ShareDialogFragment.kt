package co.netguru.android.socialslack.feature.share

import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.util.ScreenShotUtils
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.share.Sharable
import co.netguru.android.socialslack.feature.share.adapter.ShareChannelAdapter
import co.netguru.android.socialslack.feature.share.confirmation.ShareConfirmationDialogFragment
import co.netguru.android.socialslack.feature.shared.base.BaseMvpDialogFragment
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.item_channels.view.*

class ShareDialogFragment : BaseMvpDialogFragment<ShareContract.View, ShareContract.Presenter>(),
        ShareContract.View {

    companion object {
        fun <T> newInstance(selectedItem: T, mostActiveItemList: Array<T>): ShareDialogFragment
                where T : Parcelable, T : Sharable {

            val fragment = ShareDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(SELECTED_ITEM_KEY, selectedItem)
            bundle.putParcelableArray(MOST_ACTIVE_ITEM_LIST_KEY, mostActiveItemList)

            fragment.arguments = bundle
            return fragment
        }

        val TAG: String = ShareDialogFragment::class.java.simpleName

        private const val SELECTED_ITEM_KEY = "key:selected_item"
        private const val MOST_ACTIVE_ITEM_LIST_KEY = "key:most_active_item_list"
    }

    private val component by lazy {
        App.getUserComponent(context)
                .plusChannelShareComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        shareCloseBtn.setOnClickListener { presenter.onCloseButtonClick() }
        shareSendButton.setOnClickListener {
            presenter.onSendButtonClick(ScreenShotUtils.takeScreenShotByteArray(shareRootView))
        }
        presenter.prepareView(arguments.getParcelable(SELECTED_ITEM_KEY),
                arguments.getParcelableArray(MOST_ACTIVE_ITEM_LIST_KEY).toList())
    }

    override fun createPresenter() = component.getPresenter()

    override fun initShareChannelView(selectedChannel: ChannelStatistics, mostActiveChannels: List<ChannelStatistics>) {
        val adapter = ShareChannelAdapter()
        shareRecyclerView.adapter = adapter
        adapter.addChannels(mostActiveChannels)
    }

    override fun initShareUserView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showChannelName(channelName: String) {
        shareChannelNameTextView.text = resources.getString(R.string.share_recon_this, channelName)
        shareAboutSendStatisticsTextView.text = resources.getString(R.string.share_send_statistics_to, channelName)
    }

    override fun showSelectedChannelMostActiveText() {
        shareChannelStatusTextView.text = resources.getString(R.string.share_most_talkative_channel)
    }

    override fun showSelectedChannelTalkMoreText() {
        shareChannelStatusTextView.text = resources.getString(R.string.share_channel_talk_more)
    }

    override fun showSelectedChannelOnLastPosition(channelStatistics: ChannelStatistics) {
        shareMoreVertImage.visibility = View.VISIBLE
        shareLastItemContainer.visibility = View.VISIBLE
        showLastChannelData(channelStatistics)
    }

    override fun showShareConfirmationDialog(itemName: String) {
        ShareConfirmationDialogFragment.newInstance(itemName).show(fragmentManager,
                ShareConfirmationDialogFragment.TAG)
        dismiss()
    }

    override fun showLoadingView() {
        shareSendButton.visibility = View.GONE
        shareLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        shareSendButton.visibility = View.VISIBLE
        shareLoadingView.visibility = View.GONE
    }

    override fun dismissView() {
        dismiss()
    }

    override fun showError() {
        Snackbar.make(shareRecyclerView, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

    private fun showLastChannelData(channelStatistics: ChannelStatistics) {
        with(channelStatistics) {
            shareLastItem.itemChannelsPlaceNrTextView.text = (currentPositionInList.toString() + '.')
            shareLastItem.itemChannelsNameTextView.text = channelName
            shareLastItem.itemChannelsMessagesNrTextView.text = messageCount.toString()
        }
    }

    private fun initRecyclerView() {
        shareRecyclerView.setHasFixedSize(true)
        shareRecyclerView.addItemDecoration(DividerItemDecorator(
                context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
    }
}