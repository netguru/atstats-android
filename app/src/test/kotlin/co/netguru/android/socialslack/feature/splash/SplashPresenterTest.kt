package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.session.model.TokenCheck
import co.netguru.android.socialslack.data.theme.ThemeController
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@Suppress("IllegalIdentifier")
class SplashPresenterTest {

    companion object {
        const val EMPTY_STRING = ""
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val sessionController = mock<SessionController>()

    val themeController = mock<ThemeController>()

    lateinit var view: SplashContract.View

    lateinit var splashPresenter: SplashPresenter

    @Before
    fun setUp() {
        view = mock(SplashContract.View::class.java)
        splashPresenter = SplashPresenter(sessionController, themeController)
    }

    @Test
    fun `should check token validity when view attached`() {
        //given
        whenever(sessionController.isTokenValid()).thenReturn(Single.just(TokenCheck(true, EMPTY_STRING, EMPTY_STRING)))
        //when
        splashPresenter.attachView(view)
        //then
        verify(sessionController).isTokenValid()
    }

    @Test
    fun `should show main activity when token is valid`() {
        //given
        whenever(sessionController.isTokenValid()).thenReturn(Single.just(TokenCheck(true, EMPTY_STRING, EMPTY_STRING)))
        //when
        splashPresenter.attachView(view)
        //then
        verify(view).showFetchActivity()
    }

    @Test
    fun `should show login activity when token is not valid`() {
        //given
        whenever(sessionController.isTokenValid()).thenReturn(Single.just(TokenCheck(false, EMPTY_STRING, EMPTY_STRING)))
        //when
        splashPresenter.attachView(view)
        //then
        verify(view).showLoginActivity()
    }

    @Test
    fun `should show login activity when error occurs`() {
        //given
        whenever(sessionController.isTokenValid()).thenReturn(Single.error(Throwable()))
        //when
        splashPresenter.attachView(view)
        //then
        verify(view).showLoginActivity()
    }

    @After
    fun tearDown() {
        splashPresenter.detachView(false)
    }
}