package co.netguru.android.socialslack.feature.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class UsersPresenter @Inject constructor() : MvpNullObjectBasePresenter<UsersContract.View>(),
        UsersContract.Presenter {

    companion object {
        //TODO 04.08.2017 Remove while integrating API
        private const val MOCKED_DATA_SIZE = 20
    }

    override fun getUsersData() {
        view.showUsersList(getMockedData())
        view.hideLoadingView()
    }

    override fun onUserClicked(clickedUserPosition: Int) {
        view.showUserDetails(clickedUserPosition)
    }

    //TODO 04.08.2017 Remove while integrating API
    private fun getMockedData(): List<UserStatistic> {
        val userList = mutableListOf<UserStatistic>()

        for (i in 1..MOCKED_DATA_SIZE) {
            userList += UserStatistic("U2JHH3HAA",
                    "rafal.adasiewicz",
                    "Rafal Adasiewicz",
                    350 - i,
                    50,
                    300 - i,
                    350 - i,
                    7,
                    "https://avatars.slack-edge.com/2016-10-10/89333489734_47e72c65c34236dcff70_192.png",
                    i
            )
        }

        return userList
    }
}