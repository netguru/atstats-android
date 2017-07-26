package co.netguru.android.socialslack.feature.channels.profile

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.feature.channels.share.ChannelShareDialogFragment
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.channel_statistics_cardview.*
import kotlinx.android.synthetic.main.fragment_channel_profile.*
import kotlinx.android.synthetic.main.profile_header_layout.*

class ChannelProfileFragment : MvpFragment<ChannelProfile.View, ChannelProfile.Presenter>(), ChannelProfile.View {

    companion object {
        fun newInstance(channelId: String, currentPosition: Int): ChannelProfileFragment {
            val bundle = Bundle()
            bundle.putString(KEY_CHANNEL_ID, channelId)
            bundle.putInt(KEY_CHANNEL_CURRENT_POSITION, currentPosition)

            val channelProfileFragment = ChannelProfileFragment()
            channelProfileFragment.arguments = bundle

            return channelProfileFragment
        }

        val KEY_CHANNEL_ID = "key:channel_id"
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
        setUpFields()
        getPresenter().getChannelInfo()
        shareWithUserButton.setOnClickListener { presenter.onShareButtonClick()}
    }

    override fun showChannelInfo(totalMessages: Int, here: String, mentions: String) {
        totalOfHereTextView.text = here
        yourMentionsTextView.text = mentions
        secondTotalHereTextView.text = here
        totalMessagesTextView.text = totalMessages.toString()
    }

    override fun showShareDialogFragment() {
        ChannelShareDialogFragment.newInstance().show(fragmentManager, ChannelShareDialogFragment.TAG)
    }

    private fun setUpFields() {
        // The text in the header's fields will be change depending in which fragment inflate it
        messagesDetailTextView.text = resources.getString(R.string.total_messages)
        // TODO 20.07.2017 the presenter will get the name here only set the position in the list
        val channelId = arguments.getString(KEY_CHANNEL_ID)
        val channelPosition = arguments.getInt(KEY_CHANNEL_CURRENT_POSITION)
        channelNameTextView.text = resources.getString(R.string.hashtag).plus(channelId)
        rankTextView.text = channelPosition.toString()

    }

    override fun showError() {
        Snackbar.make(channelCardView, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

    override fun createPresenter(): ChannelProfile.Presenter {
        return component.getPresenter()
    }

    private fun initComponent() {
        component = App.getApplicationComponent(context)
                .plusChannelProfileComponent()
    }
}