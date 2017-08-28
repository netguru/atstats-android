package co.netguru.android.socialslack.feature.search

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface SearchContract {

    interface View : MvpView

    interface Presenter : MvpPresenter<View>
}