package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.data.team.model.Team
import co.netguru.android.socialslack.data.user.model.UserDB
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface ProfileContract {

    interface View : MvpView {

        fun showUserAndTeamInfo(user: UserDB, team: Team)

        fun changeTheme()

        fun showChangeThemeError()

        fun showTeamInfoError()
    }

    interface Presenter : MvpPresenter<View> {

        fun changeTheme()

        fun logOut()
    }
}