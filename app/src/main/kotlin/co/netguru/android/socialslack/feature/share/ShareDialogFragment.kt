package co.netguru.android.socialslack.feature.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.feature.share.adapter.ShareChannelAdapter
import co.netguru.android.socialslack.feature.share.confirmation.ShareConfirmationDialogFragment
import co.netguru.android.socialslack.feature.shared.base.BaseMvpDialogFragment
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.item_channels.*

class ShareDialogFragment : BaseMvpDialogFragment<ShareContract.View, ShareContract.Presenter>(),
        ShareContract.View {

    companion object {
        fun newInstance() = ShareDialogFragment()

        val TAG: String = ShareDialogFragment::class.java.simpleName
    }

    private val component by lazy {
        App.getApplicationComponent(context)
                .plusChannelShareComponent()
    }

    private val adapter by lazy {
        //TODO 26.07.2017 set proper adapter
        ShareChannelAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        sendButton.setOnClickListener { presenter.onSendButtonClick() }
    }

    override fun createPresenter() = component.getPresenter()

    override fun showShareConfirmationDialog() {
        ShareConfirmationDialogFragment.newInstance().show(fragmentManager,
                ShareConfirmationDialogFragment.TAG)
        dismiss()
    }

    private fun initRecyclerView() {
        shareRecyclerView.setHasFixedSize(true)
        shareRecyclerView.addItemDecoration(DividerItemDecorator(context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
        shareRecyclerView.adapter = adapter
        addMockedData()
    }

    //TODO 26.07.2017 Remove when integrating API
    private fun addMockedData() {
        adapter.addChannels(listOf(
                Channel("1", "ng-team", "creator1", false, true, 200, 1),
                Channel("2", "test-team", "creator2", false, true, 50, 2),
                Channel("3", "team-android", "creator3", false, true, 20, 3)
        ))

        itemChannelsPlaceNrTextView.text = "7."
        itemChannelsNameTextView.text = "android-internals"

        //TODO 10.07.2017 Change to messages number when it will be possible (according to SLACK API)
        itemChannelsMessagesNrTextView.text = "5"
    }
}