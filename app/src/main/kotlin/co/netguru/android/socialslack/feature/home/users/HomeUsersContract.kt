package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

internal interface HomeUsersContract {

    interface View : MvpView {
        fun setUsersWeTalkTheMost(users: List<UserStatistic>)
        fun setUsersWeWriteMost(users: List<UserStatistic>)
        fun setUsersThatWriteToUsTheMost(users: List<UserStatistic>)
    }

    interface Presenter : MvpPresenter<View> {

    }
}