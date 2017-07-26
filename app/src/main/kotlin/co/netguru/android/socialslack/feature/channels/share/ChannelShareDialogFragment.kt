package co.netguru.android.socialslack.feature.channels.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.feature.shared.base.BaseMvpDialogFragment

class ChannelShareDialogFragment : BaseMvpDialogFragment<ChannelShareContract.View, ChannelShareContract.Presenter>(),
        ChannelShareContract.View {

    companion object {
        fun newInstance() = ChannelShareDialogFragment()

        val TAG: String = ChannelShareDialogFragment::class.java.simpleName
    }

    private val component by lazy {
        App.getApplicationComponent(context)
                .plusChannelShareComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_channels_share, container, false)
    }

    override fun createPresenter() = component.getPresenter()
}