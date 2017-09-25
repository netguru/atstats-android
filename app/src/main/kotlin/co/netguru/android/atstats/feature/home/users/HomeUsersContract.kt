package co.netguru.android.atstats.feature.home.users

import co.netguru.android.atstats.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface HomeUsersContract {

    interface View : MvpView {
        fun setUsersWeTalkTheMost(users: List<UserStatistic>)
        fun setUsersWeWriteMost(users: List<UserStatistic>)
        fun setUsersThatWriteToUsTheMost(users: List<UserStatistic>)
    }

    interface Presenter : MvpPresenter<View> {

    }
}