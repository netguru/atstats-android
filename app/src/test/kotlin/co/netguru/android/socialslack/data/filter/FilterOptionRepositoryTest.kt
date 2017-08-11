package co.netguru.android.socialslack.data.filter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class FilterOptionRepositoryTest {

    val sharedPreferences: SharedPreferences = mock(SharedPreferences::class.java)

    lateinit var filterOptionRepository: FilterOptionRepository

    @Before
    fun setUp() {
        filterOptionRepository = FilterOptionRepository(sharedPreferences)
    }

    @SuppressLint("CommitPrefEdits")
    @Test
    fun `should save channels filter option in SharedPreferences`() {
        //given
        val testObserver = TestObserver<Boolean>()
        val editor = mock(SharedPreferences.Editor::class.java)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        //when
        filterOptionRepository.saveChannelsFilterOption(ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
                .subscribe(testObserver)
        //then
        verify(editor).putString(anyString(), anyString())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get channels filter option from SharedPreferences`() {
        //given
        whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn(ChannelsFilterOption.MOST_ACTIVE_CHANNEL.name)
        //when
        filterOptionRepository.getChannelsFilterOption()
        //then
        verify(sharedPreferences).getString(anyString(), anyString())
    }
}