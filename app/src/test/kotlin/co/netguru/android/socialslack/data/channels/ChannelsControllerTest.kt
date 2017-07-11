package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.model.ChannelList
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.mockito.Mockito.*
import org.junit.Rule
import org.junit.Test

@Suppress("IllegalIdentifier")
class ChannelsControllerTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsApi: ChannelsApi = mock(ChannelsApi::class.java)

    lateinit var channelsController: ChannelsController

    @Before
    fun setUp() {
        val channelList = ChannelList(true, listOf())
        whenever(channelsApi.getChannelsList()).thenReturn(Single.just(channelList))

        channelsController = ChannelsController(channelsApi)
    }

    @Test
    fun `should get channels list from api when getting channels list`() {
        //given
        val testObserver = TestObserver<List<Channel>>()
        //when
        channelsController.getChannelsList().subscribe(testObserver)
        //then
        verify(channelsApi).getChannelsList()
        testObserver.assertNoErrors()
    }
}