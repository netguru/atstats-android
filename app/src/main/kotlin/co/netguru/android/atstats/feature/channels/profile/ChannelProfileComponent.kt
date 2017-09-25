package co.netguru.android.atstats.feature.channels.profile

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ChannelProfileComponent {

    fun getPresenter(): ChannelProfilePresenter
}