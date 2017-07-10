package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter

@FragmentScope
class ChannelsPresenter : MvpNullObjectBasePresenter<ChannelsContract.View>(),
        ChannelsContract.Presenter {
}