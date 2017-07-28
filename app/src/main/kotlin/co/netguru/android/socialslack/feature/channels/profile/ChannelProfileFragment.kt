package co.netguru.android.socialslack.feature.channels.profile

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.feature.share.ShareDialogFragment
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.channel_statistics_cardview.*
import kotlinx.android.synthetic.main.fragment_channel_profile.*
import kotlinx.android.synthetic.main.profile_header_layout.*

class ChannelProfileFragment : MvpFragment<ChannelProfileContract.View, ChannelProfileContract.Presenter>(), ChannelProfileContract.View {

    companion object {
        fun newInstance(channel: Channel, mostActiveItemList: Array<Channel>, totalMessages: Int): ChannelProfileFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_CHANNEL, channel)
            bundle.putParcelableArray(KEY_CHANNEL_MOST_ACTIVE_LIST, mostActiveItemList)
            bundle.putInt(KEY_CHANNEL_TOTAL_MESSAGES, totalMessages)

            val channelProfileFragment = ChannelProfileFragment()
            channelProfileFragment.arguments = bundle

            return channelProfileFragment
        }

        val TAG: String = ChannelProfileFragment::class.java.simpleName

        private const val KEY_CHANNEL = "key:channel"
        private const val KEY_CHANNEL_MOST_ACTIVE_LIST = "key:channel_list"
        private const val KEY_CHANNEL_TOTAL_MESSAGES = "key:channel_total_message"
    }

    private lateinit var component: ChannelProfileComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater.inflate(R.layout.fragment_channel_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.apply {
            val channel: Channel = getParcelable(KEY_CHANNEL)
            setUpFields(channel, getInt(KEY_CHANNEL_TOTAL_MESSAGES))
            getPresenter().getChannelInfo(channel.id)
        }
        shareWithUserButton.setOnClickListener { presenter.onShareButtonClick() }
    }

    override fun showChannelInfo(totalHere: Int, totalMentions: Int) {
        totalOfHereTextView.text = totalHere.toString()
        yourMentionsTextView.text = totalMentions.toString()
        secondTotalHereTextView.text = totalHere.toString()
    }

    override fun showShareDialogFragment() {
        ShareDialogFragment.newInstance(arguments.getParcelable(KEY_CHANNEL),
                arguments.getParcelableArray(KEY_CHANNEL_MOST_ACTIVE_LIST))
                .show(fragmentManager, ShareDialogFragment.TAG)
    }

    private fun setUpFields(channel: Channel, totalMessage: Int) {
        messagesDetailTextView.text = resources.getString(R.string.total_messages)
        channelNameTextView.text = resources.getString(R.string.hashtag).plus(channel.name)
        rankTextView.text = channel.currentPositionInList.toString()
        totalMessagesTextView.text = totalMessage.toString()
    }

    override fun showError() {
        Snackbar.make(channelCardView, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

    override fun createPresenter(): ChannelProfileContract.Presenter {
        return component.getPresenter()
    }

    private fun initComponent() {
        component = App.getApplicationComponent(context)
                .plusChannelProfileComponent()
    }

    override fun showLoadingView() {
        progressBar.visibility = View.VISIBLE
        channelCardView.visibility = View.INVISIBLE
    }

    override fun hideLoadingView() {
        progressBar.visibility = View.INVISIBLE
        channelCardView.visibility = View.VISIBLE
    }
}