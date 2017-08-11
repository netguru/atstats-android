package co.netguru.android.socialslack.feature.users.profile

import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface UsersProfileContract {

    interface View : MvpView {
        fun initView(userStatisticsList: List<UserStatistic>, currentFilterOption: UsersFilterOption)

        fun scrollToUserPosition(userPosition: Int)

        fun showLoadingView()

        fun hideLoadingView()
    }

    interface Presenter : MvpPresenter<View> {
        fun prepareView(userStatisticsList: List<UserStatistic>, currentUserPosition: Int,
                        selectedFilterOption: UsersFilterOption)
    }
}