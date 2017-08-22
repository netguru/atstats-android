package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.app.scope.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface FetchComponent {

    fun inject(fetchActivity: FetchActivity)

    fun getPresenter(): FetchPresenter
}