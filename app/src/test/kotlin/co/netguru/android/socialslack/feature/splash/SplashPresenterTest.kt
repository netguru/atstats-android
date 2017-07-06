package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.RxImmediateSchedulerRule

import co.netguru.android.socialslack.data.session.TokenController
import co.netguru.android.socialslack.data.session.model.TokenCheck
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

import org.junit.Rule

class SplashPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxImmediateSchedulerRule()

    lateinit var tokenController: TokenController
    lateinit var view: SplashContract.View

    lateinit var splashPresenter: SplashPresenter

    @Before
    fun setUp() {

        tokenController = mock(TokenController::class.java)
        view = mock(SplashContract.View::class.java)
        splashPresenter = SplashPresenter(tokenController)
    }

    @Test
    fun shouldCheckTokenValidityWhenViewAttached() {
        //given
        `when`(tokenController.isTokenValid()).thenReturn(Single.just(TokenCheck(true)))
        //when
        splashPresenter.attachView(view)
        //then
        verify(tokenController).isTokenValid()
    }

    @Test
    fun shouldShowMainActivityWhenTokenValid() {
        //given
        `when`(tokenController.isTokenValid()).thenReturn(Single.just(TokenCheck(true)))
        //when
        splashPresenter.attachView(view)
        //then
        verify(view).showMainActivity()
    }

    @Test
    fun shouldShowLoginActivityWhenTokenInValid() {
        //given
        `when`(tokenController.isTokenValid()).thenReturn(Single.just(TokenCheck(false)))
        //when
        splashPresenter.attachView(view)
        //then
        verify(view).showLoginActivity()
    }

    @Test
    fun shouldShowLoginActivityWhenErrorOccurs() {
        //given
        `when`(tokenController.isTokenValid()).thenReturn(Single.error(Throwable()))
        //when
        splashPresenter.attachView(view)
        //then
        verify(view).showLoginActivity()
    }
}