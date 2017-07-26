package co.netguru.android.socialslack.data.filter

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import io.reactivex.observers.TestObserver
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.TestHelper.anyObject
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
        val testObserver = TestObserver<ChannelsFilterOption>()
        val channelsFilterOption = mock(ChannelsFilterOption::class.java)
        whenever(filterOptionRepository.getChannelsFilterOption()).thenReturn(channelsFilterOption)

        //when
        filterController.getChannelsFilterOption().subscribe(testObserver)
        //then
        verify(filterOptionRepository).getChannelsFilterOption()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should save channels filter option in filter option repository when saving channels filter option`() {
        //given
        val testObserver = TestObserver<ChannelsFilterOption>()
        val channelsFilterOption = mock(ChannelsFilterOption::class.java)
        whenever(filterOptionRepository.saveChannelsFilterOption(anyObject())).thenReturn(Completable.complete())

        //when
        filterController.saveChannelsFilterOption(channelsFilterOption).subscribe(testObserver)
        //then
        verify(filterOptionRepository).saveChannelsFilterOption(channelsFilterOption)
        testObserver.assertNoErrors()
    }
}