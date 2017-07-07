package co.netguru.android.socialslack.data.session

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.data.session.model.Token
import co.netguru.android.socialslack.data.session.model.TokenCheck
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class TokenControllerTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val loginApi: LoginApi = mock(LoginApi::class.java)
    val tokenRepository: TokenRepository = mock(TokenRepository::class.java)

    lateinit var tokenController: TokenController

    @Before
    fun setUp() {
        tokenController = TokenController(loginApi, tokenRepository)
    }

    @Test
    fun `should save token in token repository when saving token`() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        whenever(tokenRepository.saveToken(anyObject())).thenReturn(Completable.complete())
        //when
        tokenController.saveToken(token).subscribe(testObserver)
        //then
        verify(tokenRepository).saveToken(anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get token from token repository`() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        whenever(tokenRepository.getToken()).thenReturn(Single.just(token))
        //when
        tokenController.getToken().subscribe(testObserver)
        //then
        verify(tokenRepository).getToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should request new token from login api`() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        whenever(loginApi.requestToken(anyString(), anyString(), anyString())).thenReturn(Single.just(token))
        //when
        tokenController.requestNewToken("").subscribe(testObserver)
        //then
        verify(loginApi).requestToken(anyString(), anyString(), anyString())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get token from repository when checking validity`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token("", "", "")
        whenever(tokenRepository.getToken()).thenReturn(Single.just(token))
        //when
        tokenController.isTokenValid().subscribe(testObserver)
        //then
        verify(tokenRepository).getToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should not call api when token is empty`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token(TokenRepository.EMPTY_TOKEN, "", "")
        whenever(tokenRepository.getToken()).thenReturn(Single.just(token))
        //when
        tokenController.isTokenValid().subscribe(testObserver)
        //then
        verify(loginApi, never()).checkToken(anyString())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should call api when token is not empty`() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token("test_token", "", "")
        whenever(tokenRepository.getToken()).thenReturn(Single.just(token))
        whenever(loginApi.checkToken(anyObject())).thenReturn(Single.just(TokenCheck(true)))
        //when
        tokenController.isTokenValid().subscribe(testObserver)
        //then
        verify(loginApi).checkToken(anyString())
        testObserver.assertNoErrors()
    }
}