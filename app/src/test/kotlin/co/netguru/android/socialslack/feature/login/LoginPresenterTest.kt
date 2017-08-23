package co.netguru.android.socialslack.feature.login

import android.net.Uri
import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.session.model.Token
import co.netguru.android.socialslack.data.session.model.TokenCheck
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class LoginPresenterTest {

    companion object {
        const val EMPTY_STRING = ""
        val URI = mock<Uri>()
        val TOKEN = mock<Token>()
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val sessionController = mock<SessionController>()
    val view = mock<LoginContract.View>()

    lateinit var loginPresenter: LoginPresenter

    @Before
    fun setUp() {
        whenever(sessionController.checkToken()).thenReturn(Single.just(TokenCheck(true, EMPTY_STRING, EMPTY_STRING)))
        loginPresenter = LoginPresenter(sessionController)
        loginPresenter.attachView(view)
    }

    @Test
    fun `should get oauth uri when login button clicked`() {
        //given
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.just(URI))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(sessionController).getOauthAuthorizeUri()
    }

    @Test
    fun `should show oauth browser when getting uri successful`() {
        //given
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.just(URI))
        //when
        loginPresenter.loginButtonClicked()

        //then
        verify(view).showOAuthBrowser(anyObject())
    }

    @Test
    fun `should disable login button when getting uri successful`() {
        //given
        whenever(sessionController.getOauthAuthorizeUri()).thenReturn(Single.just(URI))
        //when
        loginPresenter.loginButtonClicked()
        //then
        verify(view).disableLoginButton()
    }

    @Test
    fun `should request new token when app authorize code received`() {
        //given
        whenever(URI.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(TOKEN))
        //when
        loginPresenter.onAppAuthorizeCodeReceived(URI)
        //then
        verify(sessionController).requestNewToken(anyString())
    }

    @Test
    fun `should save token to repository when request new token successful`() {
        //given
        whenever(URI.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(TOKEN))
        whenever(sessionController.saveToken(anyObject())).thenReturn(Completable.complete())
        //when
        loginPresenter.onAppAuthorizeCodeReceived(URI)
        //then
        verify(sessionController).saveToken(anyObject())
    }

    @Test
    fun `should check user session when request new token successful`() {
        //given
        whenever(URI.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(TOKEN))
        whenever(sessionController.saveToken(anyObject())).thenReturn(Completable.complete())
        //when
        loginPresenter.onAppAuthorizeCodeReceived(URI)
        //then
        verify(sessionController).checkToken()
    }

    @Test
    fun `should show main activity when getting token successful`() {
        //given
        whenever(URI.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(TOKEN))
        whenever(sessionController.saveToken(anyObject())).thenReturn(Completable.complete())
        //when
        loginPresenter.onAppAuthorizeCodeReceived(URI)
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

    @Test
    fun `should show error message when app authorize new code received and error occurs on check user session`() {
        //given//given
        whenever(URI.getQueryParameter(anyString())).thenReturn("")
        whenever(sessionController.requestNewToken(anyString())).thenReturn(Single.just(TOKEN))
        whenever(sessionController.checkToken()).thenReturn(Single.error(Throwable()))
        //when
        loginPresenter.onAppAuthorizeCodeReceived(URI)
        //then
        verify(view).showErrorMessage()
    }

    @After
    fun tearDown() {
        loginPresenter.detachView(false)
    }
}