package co.netguru.android.socialslack.feature.channels.share

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class ChannelSharePresenter @Inject constructor() : MvpNullObjectBasePresenter<ChannelShareContract.View>(),
        ChannelShareContract.Presenter {
}