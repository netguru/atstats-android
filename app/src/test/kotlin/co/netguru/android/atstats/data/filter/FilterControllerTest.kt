package co.netguru.android.atstats.data.filter

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import io.reactivex.Completable

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class FilterControllerTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val filterOptionRepository: FilterOptionRepository = mock(FilterOptionRepository::class.java)

    private lateinit var filterController: FilterController

    @Before
    fun setUp() {
        filterController = FilterController(filterOptionRepository)
    }

    @Test
    fun `should get channels filter option from filter option repository when getting channels filter option`() {
        //given
        val channelsFilterOption = mock(ChannelsFilterOption::class.java)
        whenever(filterOptionRepository.getChannelsFilterOption()).thenReturn(channelsFilterOption)

        //when
        val testObserver = filterController.getChannelsFilterOption().test()
        //then
        verify(filterOptionRepository).getChannelsFilterOption()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should save channels filter option in filter option repository when saving channels filter option`() {
        //given
        val channelsFilterOption = mock(ChannelsFilterOption::class.java)
        whenever(filterOptionRepository.saveChannelsFilterOption(anyObject())).thenReturn(Completable.complete())

        //when
        val testObserver = filterController.saveChannelsFilterOption(channelsFilterOption).test()
        //then
        verify(filterOptionRepository).saveChannelsFilterOption(channelsFilterOption)
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get users filter option from filter option repository when getting users filter option`() {
        //given
        val filterOption = mock(UsersFilterOption::class.java)
        whenever(filterOptionRepository.getUsersFilterOption()).thenReturn(filterOption)

        //when
        val testObserver = filterController.getUsersFilterOption().test()
        //then
        verify(filterOptionRepository).getUsersFilterOption()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should save users filter option in filter option repository when saving users filter option`() {
        //given
        val filterOption = mock(UsersFilterOption::class.java)
        whenever(filterOptionRepository.saveUsersFilterOption(anyObject())).thenReturn(Completable.complete())

        //when
        val testObserver = filterController.saveUsersFilterOption(filterOption).test()
        //then
        verify(filterOptionRepository).saveUsersFilterOption(filterOption)
        testObserver.assertNoErrors()
    }
}