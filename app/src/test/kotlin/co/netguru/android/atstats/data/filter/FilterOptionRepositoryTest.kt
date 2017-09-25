package co.netguru.android.atstats.data.filter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
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
        val editor = mock(SharedPreferences.Editor::class.java)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        //when
        val testObserver = filterOptionRepository.saveChannelsFilterOption(ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
                .test()
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

    @SuppressLint("CommitPrefEdits")
    @Test
    fun `should save users filter option in SharedPreferences`() {
        //given
        val editor = mock(SharedPreferences.Editor::class.java)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        //when
        val testObserver = filterOptionRepository.saveUsersFilterOption(UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST)
                .test()
        //then
        verify(editor).putString(anyString(), anyString())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get users filter option from SharedPreferences`() {
        //given
        whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn(UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST.name)
        //when
        filterOptionRepository.getUsersFilterOption()
        //then
        verify(sharedPreferences).getString(anyString(), anyString())
    }
}