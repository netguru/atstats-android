package co.netguru.android.socialslack.data.session

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestStatics
import co.netguru.android.socialslack.data.session.model.Token
import co.netguru.android.socialslack.data.session.model.TokenCheck
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.*

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
    fun shouldSaveTokenInTokenRepositoryWhenSavingToken() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        `when`(tokenRepository.saveToken(TestStatics.anyObject())).thenReturn(Completable.complete())
        //when
        tokenController.saveToken(token).subscribe(testObserver)
        //then
        verify(tokenRepository).saveToken(TestStatics.anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldGetTokenFromTokenRepository() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        `when`(tokenRepository.getToken()).thenReturn(Single.just(token))
        //when
        tokenController.getToken().subscribe(testObserver)
        //then
        verify(tokenRepository).getToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldRequestNewTokenFromLoginApi() {
        //given
        val testObserver = TestObserver<Token>()
        val token: Token = mock(Token::class.java)
        `when`(loginApi.requestToken(anyString(), anyString(), anyString())).thenReturn(Single.just(token))
        //when
        tokenController.requestNewToken("").subscribe(testObserver)
        //then
        verify(loginApi).requestToken(anyString(), anyString(), anyString())
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldGetTokenFromRepositoryWhenCheckingValidity() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token("", "", "")
        `when`(tokenRepository.getToken()).thenReturn(Single.just(token))
        //when
        tokenController.isTokenValid().subscribe(testObserver)
        //then
        verify(tokenRepository).getToken()
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldNotCallApiWhenTokenIsEmpty() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token(TokenRepository.EMPTY_TOKEN, "", "")
        `when`(tokenRepository.getToken()).thenReturn(Single.just(token))
        //when
        tokenController.isTokenValid().subscribe(testObserver)
        //then
        verify(loginApi, never()).checkToken(anyString())
        testObserver.assertNoErrors()
    }

    @Test
    fun shouldCallApiWhenTokenIsNotEmpty() {
        //given
        val testObserver = TestObserver<TokenCheck>()
        val token: Token = Token("test_token", "", "")
        `when`(tokenRepository.getToken()).thenReturn(Single.just(token))
        `when`(loginApi.checkToken(TestStatics.anyObject())).thenReturn(Single.just(TokenCheck(true)))
        //when
        tokenController.isTokenValid().subscribe(testObserver)
        //then
        verify(loginApi).checkToken(anyString())
        testObserver.assertNoErrors()
    }
}