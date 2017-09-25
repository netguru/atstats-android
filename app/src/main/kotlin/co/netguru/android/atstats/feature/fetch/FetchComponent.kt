package co.netguru.android.atstats.feature.fetch

import co.netguru.android.atstats.app.scope.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface FetchComponent {

    fun inject(fetchActivity: FetchActivity)

    fun getPresenter(): FetchPresenter
}