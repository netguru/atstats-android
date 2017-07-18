package co.netguru.android.socialslack.feature.channels.profile

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.channels.model.Channel
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.channel_statistics_cardview.*
import kotlinx.android.synthetic.main.fragment_channel_profile.*
import kotlinx.android.synthetic.main.profile_head_layout.*

class ChannelProfileFragment(val channel: Channel) : MvpFragment<ChannelProfile.View, ChannelProfile.Presenter>(), ChannelProfile.View {

    companion object {
        fun newInstance (channel: Channel) = ChannelProfileFragment(channel)
        val TAG:String = ChannelProfileFragment::class.java.canonicalName
    }

    private lateinit var component: ChannelProfileComponent

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater?.inflate(R.layout.fragment_channel_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFields(channel)
        getPresenter().getChannelInfo()
    }

    override fun showChannelInfo(numberMessage: Int, here: String, mentions: String) {
        totalOfHere.text = here
        yourMentions.text = mentions
        secondTotalHere.text = here
        messagesNumber.text = numberMessage.toString()
    }

    private fun setUpFields(channel: Channel) {
        messagesDetailText.text = resources.getString(R.string.total_messages)
        channelName.text = resources.getString(R.string.hashtag).plus(channel.name)
        rankTextView.text = channel.currentPositionInList.toString()

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