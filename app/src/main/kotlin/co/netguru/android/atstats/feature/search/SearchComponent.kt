package co.netguru.android.atstats.feature.search

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface SearchComponent {

    fun getPresenter() : SearchPresenter
}