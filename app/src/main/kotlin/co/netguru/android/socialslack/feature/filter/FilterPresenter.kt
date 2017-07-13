package co.netguru.android.socialslack.feature.filter

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class FilterPresenter @Inject constructor() : MvpNullObjectBasePresenter<FilterContract.View>(),
        FilterContract.Presenter {
}