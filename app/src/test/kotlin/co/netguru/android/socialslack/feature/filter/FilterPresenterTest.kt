package co.netguru.android.socialslack.feature.filter

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class FilterPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val filterController: FilterController = mock(FilterController::class.java)

    private lateinit var view: FilterContract.View
    private lateinit var filterPresenter: FilterPresenter

    @Before
    fun setUp() {
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.just(ChannelsFilterOption.MOST_ACTIVE_CHANNEL))

        view = mock(FilterContract.View::class.java)
        filterPresenter = FilterPresenter(filterController)
        filterPresenter.attachView(view)
    }

    @Test
    fun `should get channels filter option when received channel filter object`() {
        //when
        filterPresenter.filterObjectTypeReceived(FilterObjectType.CHANNELS)
        //then
        verify(filterController).getChannelsFilterOption()
    }

    @Test
    fun `should init channels filter view when getting channels filter option successful`() {
        //when
        filterPresenter.filterObjectTypeReceived(FilterObjectType.CHANNELS)
        //then
        verify(view).initChannelsFilterFragment()
    }

    @Test
    fun `should select current channels filter option when getting channels filter option successful`() {
        //when
        filterPresenter.filterObjectTypeReceived(FilterObjectType.CHANNELS)
        //then
        verify(view).selectCurrentChannelFilter(anyObject())
    }

    @Test
    fun `should save channels filter option when channels filter option changed`() {
        //given
        whenever(filterController.saveChannelsFilterOption(anyObject())).thenReturn(Completable.complete())
        //when
        filterPresenter.channelsFilterOptionChanged(ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
        //then
        verify(filterController).saveChannelsFilterOption(anyObject())
    }

    @After
    fun tearDown() {
        filterPresenter.detachView(false)
    }
}