package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.team.TeamDao
import co.netguru.android.socialslack.data.team.model.Team
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.theme.ThemeOption
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@Suppress("IllegalIdentifier")
class ProfilePresenterTest {

    companion object {
        val team = Team("","","")
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val view = mock<ProfileContract.View>()

    val themeController = mock<ThemeController>()

    val teamDao = mock<TeamDao>()

    lateinit var profilePresenter: ProfilePresenter

    @Before
    fun setUp() {
        profilePresenter = ProfilePresenter(themeController, teamDao)
        whenever(teamDao.getTeam()).thenReturn(Single.just(listOf(team)))
        profilePresenter.attachView(view)
    }

    @Test
    fun `should show team info when attach view`() {
        //then
        verify(view).showTeamInfo(team)
    }

    @Test
    fun `should show error when an error is return from the dao`() {
        //given
        whenever(teamDao.getTeam()).thenReturn(Single.error(Throwable()))

        //when
        profilePresenter.attachView(view)

        //then
        verify(view).showTeamInfoError()
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
    fun `should show error when change theme fails`() {
        //given
        whenever(themeController.getThemeOption()).thenReturn(Single.error(Throwable()))

        // when
        profilePresenter.changeTheme()

        //then
        verify(view).showChangeThemeError()
    }

    @After
    fun tearDown() {
        profilePresenter.detachView(false)
    }
}