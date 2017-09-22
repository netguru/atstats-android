package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.session.LogoutController
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.session.model.UserSession
import co.netguru.android.socialslack.data.team.TeamDao
import co.netguru.android.socialslack.data.team.model.Team
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.theme.ThemeOption
import co.netguru.android.socialslack.data.user.UsersDao
import co.netguru.android.socialslack.data.user.model.UserDB
import co.netguru.android.socialslack.data.user.profile.UsersProfileController
import co.netguru.android.socialslack.data.user.profile.model.Presence
import co.netguru.android.socialslack.data.user.profile.model.UserWithPresence
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyString

@Suppress("IllegalIdentifier")
class ProfilePresenterTest {

    companion object {
        private const val EMPTY_STRING = ""
        private val TEAM = Team(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
        private val USER = UserDB(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
        private val USER_WITH_PRESENCE = UserWithPresence(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, Presence.ACTIVE)
        private val USER_SESSION = UserSession(EMPTY_STRING, EMPTY_STRING)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val view = mock<ProfileContract.View>()

    val sessionController = mock<SessionController>()

    val themeController = mock<ThemeController>()

    val usersDao = mock<UsersDao>()

    val usersProfileController = mock<UsersProfileController>()

    val teamDao = mock<TeamDao>()

    val logoutController = mock<LogoutController>()

    lateinit var profilePresenter: ProfilePresenter

    @Before
    fun setUp() {
        profilePresenter = ProfilePresenter(themeController, sessionController, usersDao, usersProfileController, teamDao, logoutController)
        whenever(teamDao.getTeam()).thenReturn(Single.just(listOf(TEAM)))
        whenever(usersDao.getUser(anyString())).thenReturn(Single.just(USER))
        whenever(usersProfileController.getUserWithPresence(USER)).thenReturn(Single.just(USER_WITH_PRESENCE))
        whenever(sessionController.getUserSession()).thenReturn(Single.just(USER_SESSION))
        profilePresenter.attachView(view)
    }

    @Test
    fun `should show user and team info when attach view`() {
        //then
        verify(view).showUserAndTeamInfo(USER_WITH_PRESENCE, TEAM)
    }

    @Test
    fun `should change theme when the theme is changed`() {
        //given
        whenever(themeController.getThemeOption()).thenReturn(Single.just(ThemeOption.COLOURFUL))
        whenever(themeController.saveThemeOption(anyObject())).thenReturn(Completable.complete())

        // when
        profilePresenter.changeTheme()

        //then
        verify(view).changeTheme()
    }

    @Test
    fun `should store the new theme when the theme is changed`() {
        //given
        whenever(themeController.getThemeOption()).thenReturn(Single.just(ThemeOption.COLOURFUL))
        whenever(themeController.saveThemeOption(anyObject())).thenReturn(Completable.complete())

        // when
        profilePresenter.changeTheme()

        // then
        verify(themeController).saveThemeOption(anyObject())
    }

    @Test
    fun `should hide change theme button when theme is changed`() {
        //given
        whenever(themeController.getThemeOption()).thenReturn(Single.just(ThemeOption.COLOURFUL))
        whenever(themeController.saveThemeOption(anyObject())).thenReturn(Completable.complete())
        //when
        profilePresenter.changeTheme()
        //then
        verify(view).hideChangeThemeButton()
    }

    @Test
    fun `should show change theme button when theme fails`() {
        //given
        whenever(themeController.getThemeOption()).thenReturn(Single.error(Throwable()))
        //when
        profilePresenter.changeTheme()
        //then
        verify(view).showChangeThemeButton()
    }

    @Test
    fun `should log out when log out controller completes`() {
        //given
        whenever(logoutController.logout()).thenReturn(Completable.complete())

        //when
        profilePresenter.logOut()

        //then
        verify(view).logOut()
    }

    @Test
    fun `should show error when an error is return from the users dao`() {
        //given
        whenever(usersDao.getUser(anyString())).thenReturn(Single.error(Throwable()))

        //when
        profilePresenter.attachView(view)

        //then
        verify(view).showInfoError()
    }

    @Test
    fun `should show error when an error is return from the user session`() {
        //given
        whenever(sessionController.getUserSession()).thenReturn(Single.error(Throwable()))

        //when
        profilePresenter.attachView(view)

        //then
        verify(view).showInfoError()
    }

    @Test
    fun `should show error when an error is return from get user with presence`() {
        //given
        whenever(usersProfileController.getUserWithPresence(USER)).thenReturn(Single.error(Throwable()))

        //when
        profilePresenter.attachView(view)

        //then
        verify(view).showInfoError()
    }

    @Test
    fun `should show error when an error is return from the team dao`() {
        //given
        whenever(teamDao.getTeam()).thenReturn(Single.error(Throwable()))

        //when
        profilePresenter.attachView(view)

        //then
        verify(view).showInfoError()
    }

    @Test
    fun `should show error when change theme fails`() {
        //given
        whenever(themeController.getThemeOption()).thenReturn(Single.error(Throwable()))

        // when
        profilePresenter.changeTheme()

        //then
        verify(view).showChangeThemeError()
    }

    @Test
    fun `should return error when saving the theme returns error`() {
        //given
        whenever(themeController.getThemeOption()).thenReturn(Single.just(ThemeOption.COLOURFUL))
        whenever(themeController.saveThemeOption(anyObject())).thenReturn(Completable.error(Throwable()))

        // when
        profilePresenter.changeTheme()

        //then
        verify(view).showChangeThemeError()
    }

    @Test
    fun `should return error when log out controller return error`() {
        //given
        whenever(logoutController.logout()).thenReturn(Completable.error(Throwable()))

        //when
        profilePresenter.logOut()

        //then
        verify(view).showLogoutError()
    }

    @After
    fun tearDown() {
        profilePresenter.detachView(false)
    }
}