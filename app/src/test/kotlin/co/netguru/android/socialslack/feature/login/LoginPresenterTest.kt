package co.netguru.android.socialslack.feature.login

import android.net.Uri
import co.netguru.android.socialslack.RxImmediateSchedulerRule
import co.netguru.android.socialslack.TestStatics
import co.netguru.android.socialslack.data.session.TokenController
import co.netguru.android.socialslack.data.session.model.Token
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class LoginPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxImmediateSchedulerRule()

    val tokenController: TokenController = mock(TokenController::class.java)
    lateinit var view: LoginContract.View

    lateinit var loginPresenter: LoginPresenter

    @Before
    fun setUp() {
        view = mock(LoginContract.View::class.java)
        loginPresenter = LoginPresenter(tokenController)
        loginPresenter.attachView(view)
    }

    @Test
    fun shouldGetOauthUriWhenLoginButtonClicked() {
        //given
        val uri: Uri = mock(Uri::class.java)
        `when`(tokenController.getOauthAuthorizeUri()).thenReturn(Single.just(uri))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(tokenController).getOauthAuthorizeUri()
    }

    @Test
    fun shouldShowOauthBrowserWhenGettingUriSuccessful() {
        //given
        val uri: Uri = mock(Uri::class.java)
        `when`(tokenController.getOauthAuthorizeUri()).thenReturn(Single.just(uri))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(view).showOAuthBrowser(TestStatics.anyObject())
    }

    @Test
    fun shouldDisableLoginButtonWhenGettingUriSuccessful() {
        //given
        val uri: Uri = mock(Uri::class.java)
        `when`(tokenController.getOauthAuthorizeUri()).thenReturn(Single.just(uri))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(view).disableLoginButton()
    }

    @Test
    fun shouldRequestNewTokenWhenAppAuthorizeCodeReceived() {
        //given
        val uri: Uri = mock(Uri::class.java)
        val token: Token = mock(Token::class.java)
        `when`(uri.getQueryParameter(anyString())).thenReturn("")
        `when`(tokenController.requestNewToken(anyString())).thenReturn(Single.just(token))
        //when
        loginPresenter.onAppAuthorizeCodeReceived(uri)
        //then
        verify(tokenController).requestNewToken(anyString())
    }

    @Test
    fun shouldSaveTokenToRepositoryWhenRequestNewTokenSuccessful() {
        //given
        val uri: Uri = mock(Uri::class.java)
        val token: Token = mock(Token::class.java)
        `when`(uri.getQueryParameter(anyString())).thenReturn("")
        `when`(tokenController.requestNewToken(anyString())).thenReturn(Single.just(token))
        `when`(tokenController.saveToken(TestStatics.anyObject())).thenReturn(Completable.complete())
        //when
        loginPresenter.onAppAuthorizeCodeReceived(uri)
        //then
        verify(tokenController).saveToken(TestStatics.anyObject())
    }

    @Test
    fun shouldShowMainActivityWhenGettingTokenSuccessful() {
        //given
        val uri: Uri = mock(Uri::class.java)
        val token: Token = mock(Token::class.java)
        `when`(uri.getQueryParameter(anyString())).thenReturn("")
        `when`(tokenController.requestNewToken(anyString())).thenReturn(Single.just(token))
        `when`(tokenController.saveToken(TestStatics.anyObject())).thenReturn(Completable.complete())
        //when
        loginPresenter.onAppAuthorizeCodeReceived(uri)
        //then
        verify(view).showMainActivity()
    }

    @Test
    fun shouldEnableLoginButtonWhenErrorOccurs() {
        //given
        `when`(tokenController.getOauthAuthorizeUri()).thenReturn(Single.error(Throwable()))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(view).enableLoginButton()
    }

    @Test
    fun shouldShowErrorMessageWhenErrorOccurs() {
        //given
        `when`(tokenController.getOauthAuthorizeUri()).thenReturn(Single.error(Throwable()))
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