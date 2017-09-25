package co.netguru.android.atstats.data.session

import android.annotation.SuppressLint
import android.content.SharedPreferences
import co.netguru.android.atstats.data.session.model.Token
import io.reactivex.observers.TestObserver
import co.netguru.android.atstats.TestHelper.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class TokenRepositoryTest {

    val sharedPreferences: SharedPreferences = mock(SharedPreferences::class.java)

    lateinit var tokenRepository: TokenRepository

    @Before
    fun setUp() {
        tokenRepository = TokenRepository(sharedPreferences)
    }

    @Test
    fun `should get token from SharedPreferences`() {
        //given
        whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn("")
        //when
        tokenRepository.getToken()
        //then
        // 2 times because Token has 2 fields stored in SharedPreferences
        verify(sharedPreferences, times(2)).getString(anyString(), anyString())
    }

    @SuppressLint("CommitPrefEdits")
    @Test
    fun `should save token in SharedPreferences`() {
        //given
        val testObserver = TestObserver<Boolean>()
        val editor = mock(SharedPreferences.Editor::class.java)
        val token = Token("", "")
        whenever(sharedPreferences.edit()).thenReturn(editor)
        //when
        tokenRepository.saveToken(token)
                .toObservable<Boolean>()
                .subscribe(testObserver)
        //then
        // 2 times because we should store all token fields
        verify(editor, times(2)).putString(anyString(), anyString())
        testObserver.assertNoErrors()
    }
}