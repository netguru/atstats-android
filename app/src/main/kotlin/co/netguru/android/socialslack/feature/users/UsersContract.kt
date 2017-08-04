package co.netguru.android.socialslack.feature.users

import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface UsersContract {

    interface View : MvpView {
        fun showUsersList(usersList: List<UserStatistic>)

        fun showLoadingView()

        fun hideLoadingView()

        fun showUserDetails(clickedUserPosition: Int)
    }

    interface Presenter : MvpPresenter<View> {

        fun getUsersData()

        fun onUserClicked(clickedUserPosition: Int)
    }
}