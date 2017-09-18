package co.netguru.android.socialslack.feature.users

import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface UsersContract {

    interface View : MvpView {
        fun showUsersList(usersList: List<UserStatistic>)

        fun showError()

        fun showLoadingView()

        fun hideLoadingView()

        fun showUserDetails(clickedUserPosition: Int, selectedFilterOption: UsersFilterOption)

        fun showFilterView()

        fun showSearchView()

        fun changeSelectedFilterOption(selectedFilterOption: UsersFilterOption)

        fun showFilterOptionError()
    }

    interface Presenter : MvpPresenter<View> {

        fun getCurrentFilterOption()

        fun getUsersData()

        fun onUserClicked(clickedUserPosition: Int)

        fun filterButtonClicked()

        fun sortRequestReceived(usersList: List<UserStatistic>)

        fun searchButtonClicked()
    }
}