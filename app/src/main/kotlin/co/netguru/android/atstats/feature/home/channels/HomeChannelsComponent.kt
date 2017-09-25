package co.netguru.android.atstats.feature.home.channels

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface HomeChannelsComponent {

    fun getPresenter(): HomeChannelsPresenter
}