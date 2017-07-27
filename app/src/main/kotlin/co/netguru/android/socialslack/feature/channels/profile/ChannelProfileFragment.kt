package co.netguru.android.socialslack.feature.channels.profile

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.channel_statistics_cardview.*
import kotlinx.android.synthetic.main.fragment_channel_profile.*
import kotlinx.android.synthetic.main.profile_header_layout.*

class ChannelProfileFragment : MvpFragment<ChannelProfileContract.View, ChannelProfileContract.Presenter>(), ChannelProfileContract.View {

    companion object {
        fun newInstance(channelId: String, channelName: String, totalMessages: Int, currentPosition: Int): ChannelProfileFragment {
            val bundle = Bundle()
            bundle.putString(KEY_CHANNEL_ID, channelId)
            bundle.putString(KEY_CHANNEL_NAME, channelName)
            bundle.putInt(KEY_CHANNEL_TOTAL_MESSAGES, totalMessages)
            bundle.putInt(KEY_CHANNEL_CURRENT_POSITION, currentPosition)

            val channelProfileFragment = ChannelProfileFragment()
            channelProfileFragment.arguments = bundle

            return channelProfileFragment
        }

        val KEY_CHANNEL_ID = "key:channel_id"
        val KEY_CHANNEL_NAME = "key:channel_name"
        val KEY_CHANNEL_TOTAL_MESSAGES = "key:channel_total_message"
        val KEY_CHANNEL_CURRENT_POSITION = "key:channel_current_position"
        val TAG: String = ChannelProfileFragment::class.java.simpleName
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
            setUpFields(getString(KEY_CHANNEL_NAME), getInt(KEY_CHANNEL_TOTAL_MESSAGES), getInt(KEY_CHANNEL_CURRENT_POSITION))
        }
        getPresenter().getChannelInfo(arguments.getString(KEY_CHANNEL_ID))
    }

    override fun showChannelInfo(totalHere: Int, totalMentions: Int) {
        totalOfHereTextView.text = totalHere.toString()
        yourMentionsTextView.text = totalMentions.toString()
        secondTotalHereTextView.text = totalHere.toString()
    }

    private fun setUpFields(channelName: String, totalMessage: Int, rank: Int) {
        messagesDetailTextView.text = resources.getString(R.string.total_messages)
        channelNameTextView.text = resources.getString(R.string.hashtag).plus(channelName)
        rankTextView.text = rank.toString()
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
}