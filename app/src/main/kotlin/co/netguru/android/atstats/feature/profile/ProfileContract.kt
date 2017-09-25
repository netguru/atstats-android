package co.netguru.android.atstats.feature.profile

import co.netguru.android.atstats.data.team.model.Team
import co.netguru.android.atstats.data.user.profile.model.UserWithPresence
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface ProfileContract {

    interface View : MvpView {

        fun showUserAndTeamInfo(user: UserWithPresence, team: Team)

        fun changeTheme()

        fun hideChangeThemeButton()

        fun showChangeThemeButton()

        fun logOut()

        fun showInfoError()

        fun showChangeThemeError()

        fun showLogoutError()
    }

    interface Presenter : MvpPresenter<View> {

        fun changeTheme()

        fun logOut()
    }
}