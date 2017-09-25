package co.netguru.android.atstats.feature.share

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ShareComponent {

    fun getPresenter() : SharePresenter
}