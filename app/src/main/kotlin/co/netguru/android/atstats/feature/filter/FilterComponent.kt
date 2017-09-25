package co.netguru.android.atstats.feature.filter

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FilterComponent {

    fun getPresenter() : FilterPresenter
}