package co.netguru.android.socialslack.feature.home.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.util.ViewUtils.roundImageView
import kotlinx.android.synthetic.main.dashboard_statistics_card.view.*
import kotlinx.android.synthetic.main.fragment_home_dashboard.*

class HomeDashboardFragment : Fragment() {

    companion object {
        fun newInstance() = HomeDashboardFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home_dashboard, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAvatar.roundImageView()
        initStatistics()
    }

    private fun initStatistics() {
        // TODO 12.07.17 remove mock data
        statistic1.dashboardStatisticIcon.setImageResource(R.drawable.profile_messages)
        statistic1.dashboardStatisticValue.text = "35"
        statistic1.dashboardStatisticText.text = getString(R.string.statistic_messages_sent)

        statistic2.dashboardStatisticIcon.setImageResource(R.drawable.profile_mentions)
        statistic2.dashboardStatisticValue.text = "35"
        statistic2.dashboardStatisticText.text = getString(R.string.statistic_mentions)

        statistic3.dashboardStatisticIcon.setImageResource(R.drawable.profile_priv_msg)
        statistic3.dashboardStatisticValue.text = "2345"
        statistic3.dashboardStatisticText.text = getString(R.string.statistic_private_messages)

        statistic4.dashboardStatisticIcon.setImageResource(R.drawable.profile_sent_privs)
        statistic4.dashboardStatisticValue.text = "35"
        statistic4.dashboardStatisticText.text = getString(R.string.statistic_sent_private_messages)

        statistic5.dashboardStatisticIcon.setImageResource(R.drawable.profile_read_msg)
        statistic5.dashboardStatisticValue.text = "2345"
        statistic5.dashboardStatisticText.text = getString(R.string.statistic_total_read_messages)

        statistic6.dashboardStatisticIcon.setImageResource(R.drawable.profile_received_priv_msgs)
        statistic6.dashboardStatisticValue.text = "35"
        statistic6.dashboardStatisticText.text = getString(R.string.statistic_received_private_messages)
    }
}