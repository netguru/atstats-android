package co.netguru.android.socialslack.feature.search

import co.netguru.android.socialslack.app.scope.FragmentScope
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class SearchPresenter @Inject constructor()
    : MvpNullObjectBasePresenter<SearchContract.View>(), SearchContract.Presenter {
}