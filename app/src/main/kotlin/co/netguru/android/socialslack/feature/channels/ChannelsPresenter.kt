package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class ChannelsPresenter @Inject constructor() : MvpNullObjectBasePresenter<ChannelsContract.View>(),
        ChannelsContract.Presenter {
}