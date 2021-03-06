package co.netguru.android.atstats.data.session

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.session.model.Token
import co.netguru.android.atstats.data.session.model.TokenCheck
import co.netguru.android.atstats.data.session.model.UserSession
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class SessionControllerTest {

    companion object {
        const val EMPTY_STRING = ""
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val loginApi = mock<LoginApi>()
    val tokenRepository = mock<TokenRepository>()
    val userSessionRespository = mock<UserSessionRepository>()

    lateinit var sessionController: SessionController

    @Before
    fun setUp() {
        sessionController = SessionController(loginApi, tokenRepository, userSessionRespository)
    }

    @Test
    fun `should save token in token repository when saving token`() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        whenever(tokenRepository.saveToken(anyObject())).thenReturn(Completable.complete())
        //when
        sessionController.saveToken(token).subscribe(testObserver)
        //then
        verify(tokenRepository).saveToken(anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get token from token repository`() {
        //given
        val testObserver = TestObserver<Token>()
        val token = mock<Token>()
        whenever(tokenRepository.getToken()).thenReturn(token)
        //when
        sessionController.getToken().subscribe(testObserver)
        //then
        verify(tokenRepository).getToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get UserSession from UserSessionRepository`() {
        //given
        val testObserver = TestObserver<UserSession>()
        val userSession = mock<UserSession>()
        whenever(userSessionRespository.getUserSession()).thenReturn(userSession)
        //when
        sessionController.getUserSession().subscribe(testObserver)
        //then
        verify(userSessionRespository).getUserSession()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should request new token from login api`() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        whenever(loginApi.requestToken(anyString(), anyString(), anyString())).thenReturn(Single.just(token))
        //when
        sessionController.requestNewToken("").subscribe(testObserver)
        //then
        verify(loginApi).requestToken(anyString(), anyString(), anyString())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get token from repository when checking validity`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token("", "")
        whenever(tokenRepository.getToken()).thenReturn(token)
        //when
        sessionController.isTokenValid().subscribe(testObserver)
        //then
        verify(tokenRepository).getToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should not call api when token is empty`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token(TokenRepository.EMPTY_TOKEN, "")
        whenever(tokenRepository.getToken()).thenReturn(token)
        //when
        sessionController.isTokenValid().subscribe(testObserver)
        //then
        verify(loginApi, never()).checkToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should call api when token is not empty`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token("test_token", "")
        whenever(tokenRepository.getToken()).thenReturn(token)
        whenever(loginApi.checkToken()).thenReturn(Single.just(TokenCheck(true, EMPTY_STRING, EMPTY_STRING)))
        whenever(userSessionRespository.saveUserSession(anyObject())).thenReturn(Completable.complete())
        //when
        sessionController.isTokenValid().subscribe(testObserver)
        //then
        verify(loginApi).checkToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should store UserSession when token is not empty`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token("test_token", "")
        val userSession = UserSession("userId", "teamId")
        whenever(tokenRepository.getToken()).thenReturn(token)
        whenever(loginApi.checkToken()).thenReturn(Single.just(TokenCheck(true, "teamId", "userId")))
        whenever(userSessionRespository.saveUserSession(anyObject())).thenReturn(Completable.complete())
        //when
        sessionController.isTokenValid().subscribe(testObserver)
        //then
        verify(userSessionRespository).saveUserSession(anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should remove token and usersession when remove session`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        whenever(tokenRepository.removeToken()).thenReturn(Completable.complete())
        whenever(userSessionRespository.removeUserSession()).thenReturn(Completable.complete())
        //when
        sessionController.removeSession().subscribe(testObserver)
        //then
        verify(tokenRepository).removeToken()
        verify(userSessionRespository).removeUserSession()
        testObserver.assertNoErrors()

    }
}