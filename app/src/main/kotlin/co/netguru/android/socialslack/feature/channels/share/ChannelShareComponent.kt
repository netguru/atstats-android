package co.netguru.android.socialslack.feature.channels.share

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ChannelShareComponent {

    fun getPresenter() : ChannelSharePresenter
}