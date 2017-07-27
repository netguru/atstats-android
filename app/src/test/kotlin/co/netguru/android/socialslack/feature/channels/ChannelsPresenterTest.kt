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
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

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
        whenever(filterController.getChannelsFilterOption()).thenReturn(ChannelsFilterOption.MOST_ACTIVE_CHANNEL)

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
    fun `should show error when error occurs`() {
        //given
        whenever(channelsController.getChannelsList()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.getChannelsFromServer()
        //then
        verify(view).showError()
    }

    @After
    fun tearDown() {
        channelsPresenter.detachView(false)
    }
}