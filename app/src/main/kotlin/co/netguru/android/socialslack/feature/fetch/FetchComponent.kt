package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.app.scope.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface FetchComponent {

    fun getPresenter(): FetchPresenter
}