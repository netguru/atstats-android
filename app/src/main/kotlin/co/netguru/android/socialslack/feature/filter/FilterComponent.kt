package co.netguru.android.socialslack.feature.filter

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FilterComponent {

    fun getPresenter() : FilterPresenter
}