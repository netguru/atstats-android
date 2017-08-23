package co.netguru.android.socialslack.feature.login

import android.net.Uri
import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.session.model.Token
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.TestHelper.anyObject
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class LoginPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val sessionController: SessionController = mock(SessionController::class.java)
    lateinit var view: LoginContract.View

    lateinit var loginPresenter: LoginPresenter

    @Before
    fun setUp() {
        view = mock(LoginContract.View::class.java)
        loginPresenter = LoginPresenter(sessionController)
        loginPresenter.attachView(view)
    }

    @Test
    fun `should get oauth uri when login button clicked`() {
        //given
        val uri: Uri = mock(Uri::class.java)
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.just(uri))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(sessionController).getOauthAuthorizeUri()
    }

    @Test
    fun `should show oauth browser when getting uri successful`() {
        //given
        val uri: Uri = mock(Uri::class.java)
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.just(uri))
        //when
        loginPresenter.loginButtonClicked()

        //then
        verify(view).showOAuthBrowser(anyObject())
    }

    @Test
    fun `should disable login button when getting uri successful`() {
        //given
        val uri: Uri = mock(Uri::class.java)
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.just(uri))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(view).disableLoginButton()
    }

    @Test
    fun `should request new token when app authorize code received`() {
        //given
        val uri: Uri = mock(Uri::class.java)
        val token: Token = mock(Token::class.java)
        whenever(uri.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(token))
        //when
        loginPresenter.onAppAuthorizeCodeReceived(uri)
        //then
        verify(sessionController).requestNewToken(anyString())
    }

    @Test
    fun `should save token to repository when request new token successful`() {
        //given
        val uri: Uri = mock(Uri::class.java)
        val token: Token = mock(Token::class.java)
        whenever(uri.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(token))
        whenever(sessionController.saveToken(anyObject())).thenReturn(Completable.complete())
        //when
        loginPresenter.onAppAuthorizeCodeReceived(uri)
        //then
        verify(sessionController).saveToken(anyObject())
    }

    @Test
    fun `should show main activity when getting token successful`() {
        //given
        val uri: Uri = mock(Uri::class.java)
        val token: Token = mock(Token::class.java)
        whenever(uri.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(token))
        whenever(sessionController.saveToken(anyObject())).thenReturn(Completable.complete())
        //when
        loginPresenter.onAppAuthorizeCodeReceived(uri)
        //then
        verify(view).showMainActivity()
    }

    @Test
    fun `should enable login button when error occurs`() {
        //given
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.error(Throwable()))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(view).enableLoginButton()
    }

    @Test
    fun `should show error message when error occurs`() {
        //given
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.error(Throwable()))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(view).showErrorMessage()
    }

    @After
    fun tearDown() {
        loginPresenter.detachView(false)
    }
}