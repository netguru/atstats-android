package co.netguru.android.atstats.data.session

import android.content.SharedPreferences
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.session.model.UserSession
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class UserSessionRepositoryTest {

    val sharedPreferences = mock<SharedPreferences>()

    lateinit var userSessionRepository: UserSessionRepository

    @Before
    fun setUp() {
        userSessionRepository = UserSessionRepository(sharedPreferences)
    }

    @Test
    fun `should get UserSession from SharedPreferences`() {
        // given
        whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn("")
        // when
        userSessionRepository.getUserSession()
        // then
        // 2 times because UserSession has 2 fields stored in SharedPreferences
        verify(sharedPreferences, times(2)).getString(anyString(), anyString())
    }

    @Test
    fun `should store UserSession in SharedPreferences`() {
        //given
        val testObserver = TestObserver<Boolean>()
        val editor = mock(SharedPreferences.Editor::class.java)
        val userSession = UserSession("","")
        whenever(sharedPreferences.edit()).thenReturn(editor)
        // when
        userSessionRepository.saveUserSession(userSession)
                .toObservable<Boolean>()
                .subscribe(testObserver)
        // then
        // 2 times because we should store all user session fields
        verify(editor, times(2)).putString(anyString(), anyString())
        testObserver.assertNoErrors()
    }
}