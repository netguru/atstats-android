package co.netguru.android.socialslack.feature.home.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface HomeChannelsComponent {

    fun getPresenter(): HomeChannelsPresenter
}