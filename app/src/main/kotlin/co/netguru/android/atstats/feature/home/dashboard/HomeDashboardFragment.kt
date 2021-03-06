package co.netguru.android.atstats.feature.home.dashboard

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.app.GlideApp
import co.netguru.android.atstats.common.extensions.getAttributeDrawable
import co.netguru.android.atstats.common.extensions.inflate
import co.netguru.android.atstats.common.util.ViewUtils.roundImageView
import co.netguru.android.atstats.data.shared.RandomMessageProvider
import co.netguru.android.atstats.feature.home.dashboard.model.ChannelsCount
import co.netguru.android.atstats.feature.home.dashboard.model.DirectChannelsCount
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.dashboard_statistics_card.view.*
import kotlinx.android.synthetic.main.fragment_home_dashboard.*

class HomeDashboardFragment :
        MvpFragment<HomeDashboardContract.View, HomeDashboardContract.Presenter>(), HomeDashboardContract.View {

    val component by lazy { App.getUserComponent(context).plusHomeDashboardComponent() }

    override fun createPresenter(): HomeDashboardPresenter = component.getPresenter()

    companion object {
        fun newInstance() = HomeDashboardFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home_dashboard)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAvatar.roundImageView()
        initStatistics()
    }

    override fun showProfile(username: String?, avatarUrl: String?) {
        userNameTextView.text = getString(R.string.username, username)
        GlideApp.with(this)
                .load(avatarUrl)
                .placeholder(context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                .error(context.getAttributeDrawable(R.attr.userPlaceholderDrawable))
                .transforms(arrayOf(CenterInside(), RoundedCorners(resources.getDimension(R.dimen.item_user_avatar_radius).toInt())))
                .into(userAvatar)
    }

    override fun showCounts(channelsCount: ChannelsCount, directChannelsCount: DirectChannelsCount) {
        statistic1.dashboardStatisticValue.text = channelsCount.totalMessageSent.toString()
        statistic2.dashboardStatisticValue.text = channelsCount.totalMentions.toString()
        statistic3.dashboardStatisticValue.text = (directChannelsCount.receivedMessages + directChannelsCount.sentMessages)
                .toString()
        statistic4.dashboardStatisticValue.text = directChannelsCount.sentMessages.toString()
        statistic5.dashboardStatisticValue.text = channelsCount.totalMessagesReceived.toString()
        statistic6.dashboardStatisticValue.text = directChannelsCount.receivedMessages.toString()
    }

    override fun showProfileError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(userAvatar, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

    override fun showCountError() {
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(userAvatar, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

    private fun initStatistics() {
        statistic1.dashboardStatisticIcon.setImageResource(context.getAttributeDrawable(R.attr.profileMessagesDrawable))
        statistic1.dashboardStatisticText.text = getString(R.string.statistic_messages_sent)

        statistic2.dashboardStatisticIcon.setImageResource(context.getAttributeDrawable(R.attr.profileMentionsDrawable))
        statistic2.dashboardStatisticText.text = getString(R.string.statistic_mentions)

        statistic3.dashboardStatisticIcon.setImageResource(context.getAttributeDrawable(R.attr.profilePrivMsgDrawable))
        statistic3.dashboardStatisticText.text = getString(R.string.statistic_private_messages)

        statistic4.dashboardStatisticIcon.setImageResource(context.getAttributeDrawable(R.attr.profileSentPrivsDrawable))
        statistic4.dashboardStatisticText.text = getString(R.string.statistic_sent_private_messages)

        statistic5.dashboardStatisticIcon.setImageResource(context.getAttributeDrawable(R.attr.profileReadMsgDrawable))
        statistic5.dashboardStatisticText.text = getString(R.string.statistic_total_read_messages)

        statistic6.dashboardStatisticIcon.setImageResource(context.getAttributeDrawable(R.attr.profileReceivedPrivMsgsDrawable))
        statistic6.dashboardStatisticText.text = getString(R.string.statistic_received_private_messages)
    }
}