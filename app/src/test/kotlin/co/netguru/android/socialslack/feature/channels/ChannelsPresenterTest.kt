package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class ChannelsPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsController: ChannelsProvider = mock(ChannelsProvider::class.java)
    val filterController: FilterController = mock(FilterController::class.java)
    lateinit var view: ChannelsContract.View

    lateinit var channelsPresenter: ChannelsPresenter

    @Before
    fun setUp() {
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.just(ChannelsFilterOption.MOST_ACTIVE_CHANNEL))

        view = mock(ChannelsContract.View::class.java)
        channelsPresenter = ChannelsPresenter(channelsController, filterController)
        channelsPresenter.attachView(view)
    }

    @Test
    fun `should show channels list for user when getting channels successful`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf()))
        //when
        channelsPresenter.getChannelsFromServer()
        //then
        verify(view).showChannels(anyObject())
    }

    @Test
    fun `should show error when getting channels from server failed`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.getChannelsFromServer()
        //then
        verify(view).showError()
    }

    @Test
    fun `should show loading view when getting channels from server`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf()))
        //when
        channelsPresenter.getChannelsFromServer()
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should hide loading view when getting channels from server successful`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf()))
        //when
        channelsPresenter.getChannelsFromServer()
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should hide loading view when getting channels from server failed`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.getChannelsFromServer()
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should set current filter option when getting filter option successful`() {
        //when
        channelsPresenter.getCurrentFilterOption()
        //then
        verify(view).setCurrentFilterOptionText(ArgumentMatchers.anyInt())
    }

    @Test
    fun `should show filter option error when getting filter option failed`() {
        //given
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.getCurrentFilterOption()
        //then
        verify(view).showFilterOptionError()
    }

    @Test
    fun `should show filter view when filter button clicked`() {
        //when
        channelsPresenter.filterButtonClicked()
        //then
        verify(view).showFilterView()
    }

    @Test
    fun `should get filter option from from filter controller when sort request received`() {
        //when
        channelsPresenter.sortRequestReceived(listOf())
        //then
        verify(filterController).getChannelsFilterOption()
    }

    @Test
    fun `should show sorted channels list when sort request successful`() {
        //when
        channelsPresenter.sortRequestReceived(listOf())
        //then
        verify(view).showChannels(anyObject())
    }

    @Test
    fun `should show updated filter option when sort request successful`() {
        //when
        channelsPresenter.sortRequestReceived(listOf())
        //then
        verify(view).setCurrentFilterOptionText(ArgumentMatchers.anyInt())
    }

    @Test
    fun `should show filter option error when sort request failed`() {
        //given
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.sortRequestReceived(listOf())
        //then
        verify(view).showFilterOptionError()
    }

    @Test
    fun `should show loading view when sort request received`() {
        //when
        channelsPresenter.sortRequestReceived(listOf())
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should hide loading view when sort request received successful`() {
        //when
        channelsPresenter.sortRequestReceived(listOf())
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should hide loading view when sort request received failed`() {
        //given
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.sortRequestReceived(listOf())
        //then
        verify(view).hideLoadingView()
    }

    @After
    fun tearDown() {
        channelsPresenter.detachView(false)
    }
}