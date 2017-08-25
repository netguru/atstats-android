package co.netguru.android.socialslack.feature.home.dashboard

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject


class HomeDashboardPresenter @Inject constructor():
        MvpNullObjectBasePresenter<HomeDashboardContract.View>(), HomeDashboardContract.Presenter {

}