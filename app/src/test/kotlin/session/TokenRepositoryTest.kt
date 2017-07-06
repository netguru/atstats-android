package session

import android.annotation.SuppressLint
import android.content.SharedPreferences
import co.netguru.android.socialslack.data.session.TokenRepository
import co.netguru.android.socialslack.data.session.model.Token
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class TokenRepositoryTest {

    val sharedPreferences: SharedPreferences = mock(SharedPreferences::class.java)

    lateinit var tokenRepository: TokenRepository

    @Before
    fun setUp() {
        tokenRepository = TokenRepository(sharedPreferences)
    }

    @Test
    fun shouldGetTokenFromSharedPreferences() {
        //given
        `when`(sharedPreferences.getString(anyString(), anyString())).thenReturn("")
        //when
        tokenRepository.getToken()
        //then
        // 3 times because Token has 3 fields stored in SharedPreferences
        verify(sharedPreferences, times(3)).getString(anyString(), anyString())
    }

    @SuppressLint("CommitPrefEdits")
    @Test
    fun shouldSaveTokenInSharedPreferences() {
        //given
        val testObserver = TestObserver<Boolean>()
        val editor = mock(SharedPreferences.Editor::class.java)
        val token = Token("", "", "")
        `when`(sharedPreferences.edit()).thenReturn(editor)
        //when
        tokenRepository.saveToken(token)
                .toObservable<Boolean>()
                .subscribe(testObserver)
        //then
        // 3 times because we should store all token fields
        verify(editor, times(3)).putString(anyString(), anyString())
        testObserver.assertNoErrors()
    }
}