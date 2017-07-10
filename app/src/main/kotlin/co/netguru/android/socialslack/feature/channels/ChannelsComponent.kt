package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ChannelsComponent {

    fun getPresenter() : ChannelsPresenter
}