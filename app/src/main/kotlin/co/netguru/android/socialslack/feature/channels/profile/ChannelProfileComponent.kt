package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent


@FragmentScope
@Subcomponent
interface ChannelProfileComponent {

    fun getPresenter(): ChannelProfilePresenter
}