package co.netguru.android.socialslack.feature.channels.profile

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.feature.share.ShareDialogFragment
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.channel_statistics_cardview.*
import kotlinx.android.synthetic.main.fragment_channel_profile.*
import kotlinx.android.synthetic.main.profile_header_layout.*

class ChannelProfileFragment : MvpFragment<ChannelProfileContract.View, ChannelProfileContract.Presenter>(), ChannelProfileContract.View {

    companion object {
        fun newInstance(channel: Channel, mostActiveItemList: Array<Channel>): ChannelProfileFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_CHANNEL, channel)
            bundle.putParcelableArray(KEY_CHANNEL_MOST_ACTIVE_LIST, mostActiveItemList)

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
        return container?.inflate(R.layout.fragment_channel_profile)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.apply {
            val channel: Channel = getParcelable(KEY_CHANNEL)
            setUpFields(channel)
            getPresenter().getChannelInfo(channel.id)
        }
        shareWithUserButton.setOnClickListener { presenter.onShareButtonClick() }
    }

    override fun showChannelInfo(totalMessages: Int, totalHere: Int, totalMentions: Int) {
        totalMessagesTextView.text = totalMessages.toString()
        totalOfHereTextView.text = totalHere.toString()
        yourMentionsTextView.text = totalMentions.toString()
        secondTotalHereTextView.text = totalHere.toString()
    }

    override fun showShareDialogFragment() {
        val channel: Channel = arguments.getParcelable(KEY_CHANNEL)
        val channelArray: Array<Channel> = arguments.getParcelableArray(KEY_CHANNEL_MOST_ACTIVE_LIST)
                .filterIsInstance(Channel::class.java).toTypedArray()

        ShareDialogFragment.newInstance(channel, channelArray).show(fragmentManager, ShareDialogFragment.TAG)
    }

    private fun setUpFields(channel: Channel) {
        messagesDetailTextView.text = resources.getString(R.string.total_messages)
        channelNameTextView.text = resources.getString(R.string.hashtag).plus(channel.name)
        rankTextView.text = channel.currentPositionInList.toString()
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