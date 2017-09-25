package co.netguru.android.atstats.feature.channels

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ChannelsComponent {

    fun getPresenter() : ChannelsPresenter
}