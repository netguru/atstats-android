package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.data.channels.ChannelsController
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.*
import org.junit.Rule
import org.junit.Test

@Suppress("IllegalIdentifier")
class ChannelsPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsController: ChannelsController = mock(ChannelsController::class.java)
    lateinit var view: ChannelsContract.View

    lateinit var channelsPresenter: ChannelsPresenter

    @Before
    fun setUp() {
        view = mock(ChannelsContract.View::class.java)
        channelsPresenter = ChannelsPresenter(channelsController)
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