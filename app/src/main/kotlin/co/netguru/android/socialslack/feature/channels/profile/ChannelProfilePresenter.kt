package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.feature.channels.profile.ChannelProfile.Presenter
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class ChannelProfilePresenter @Inject constructor() :
        MvpNullObjectBasePresenter<ChannelProfile.View>(), Presenter {

    override fun getChannelInfo() {
        // TODO get the actual numbers
        view.showChannelInfo(1234, "17", "34")
    }

}


