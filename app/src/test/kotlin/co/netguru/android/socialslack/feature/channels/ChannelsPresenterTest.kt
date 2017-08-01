package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.model.Channel
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

    companion object {
        private val CHANNEL_MOST_ACTIVE = Channel("1", "", "", false, false, 4, 1)
        private val CHANNEL1 = Channel("11", "", "", false, false, 4, 1)
        private val CHANNEL2 = Channel("2", "", "", false, false, 3, 2)
        private val CHANNEL3 = Channel("3", "", "", false, false, 2, 3)
        private val CHANNEL33 = Channel("33", "", "", false, false, 2, 3)
        private val CHANNEL4 = Channel("4", "", "", false, false, 1, 4)
    }

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

    @Test
    fun `should get channels filter option when on channel click`() {
        //when
        channelsPresenter.onChannelClick(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(filterController).getChannelsFilterOption()
    }

    @Test
    fun `should show channel details when getting most active channels successful`() {
        //when
        channelsPresenter.onChannelClick(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(view).showChannelDetails(anyObject(), anyObject())
    }

    @Test
    fun `should show selected channel details with 3 top channels from list when current filter option is most active`() {
        //when
        channelsPresenter.onChannelClick(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(view).showChannelDetails(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3))
    }

    @Test
    fun `should sort most active channels list when current filter option is not most active`() {
        //TODO 31.07.2017 Change membersNumber to messages number in mocks when available
        //given
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.just(ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE))
        //when
        channelsPresenter.onChannelClick(CHANNEL_MOST_ACTIVE, listOf(CHANNEL4, CHANNEL3, CHANNEL2, CHANNEL_MOST_ACTIVE))
        //verify
        verify(view).showChannelDetails(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3))
    }

    @Test
    fun `should change selected channel position in most active list when other channel has the same messages number and it's position is higher`() {
        //TODO 31.07.2017 Change membersNumber to messages number in mocks when available
        //when
        channelsPresenter.onChannelClick(CHANNEL_MOST_ACTIVE, listOf(CHANNEL1, CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //verify
        verify(view).showChannelDetails(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL1, CHANNEL2))
    }

    @Test
    fun `should add selected channel to most active list when missing and last item from list has the same current position number`() {
        //when
        channelsPresenter.onChannelClick(CHANNEL33, listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL4))
        //verify
        verify(view).showChannelDetails(CHANNEL33, listOf(CHANNEL1, CHANNEL2, CHANNEL33))
    }

    @After
    fun tearDown() {
        channelsPresenter.detachView(false)
    }
}