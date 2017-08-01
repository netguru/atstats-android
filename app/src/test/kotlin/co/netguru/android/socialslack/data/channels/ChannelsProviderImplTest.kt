package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
<<<<<<< HEAD
import co.netguru.android.socialslack.data.channels.model.ChannelHistory
import co.netguru.android.socialslack.data.channels.model.ChannelList
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
=======
import co.netguru.android.socialslack.data.channels.model.*
>>>>>>> master
import io.reactivex.Single
import org.junit.Before
import org.mockito.Mockito.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@Suppress("IllegalIdentifier")
class ChannelsProviderImplTest {

    companion object {
<<<<<<< HEAD
        @JvmStatic
        val LATEST = 1000F
        @JvmStatic
        val USER = "user"
        @JvmStatic
        val CHANNEL = "channel"
=======
        private const val LATEST = 1000F
        private const val USER = "user"

        private val SUCCESSFUL_FILE_OBJECT = FileUploadResponse(true, "",
                FileObject("" ,"", "", ""))

        private val FAILED_FILE_OBJECT = FileUploadResponse(false, "no_content",
                FileObject("" ,"", "", ""))
>>>>>>> master
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsApi: ChannelsApi = mock(ChannelsApi::class.java)

    lateinit var channelsProvider: ChannelsProvider

    @Before
    fun setUp() {
        val channelList = ChannelList(true, listOf())
        val channelHistory = ChannelHistory(true, LATEST, listOf(ChannelMessage(1, ChannelMessage.MESSAGE_TYPE, CHANNEL, LATEST, USER, USER)), false)
        whenever(channelsApi.getChannelsList()).thenReturn(Single.just(channelList))
        whenever(channelsApi.getChannelsHistory(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyBoolean(), ArgumentMatchers.anyFloat(),
                ArgumentMatchers.anyFloat(), ArgumentMatchers.anyBoolean()))
                .thenReturn(Single.just(channelHistory))

        channelsProvider = ChannelsProviderImpl(channelsApi)
    }

    @Test
    fun `should get channels list from api when getting channels list`() {
        //when
        val testObserver =  channelsProvider.getChannelsList().test()
        //then
        verify(channelsApi).getChannelsList()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should upload file to api when uploading file to channel`() {
        //given
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(SUCCESSFUL_FILE_OBJECT))
        //when
        val testObserver = channelsProvider.uploadFileToChannel("channel", byteArrayOf()).test()
        //then
        verify(channelsApi).uploadFileToChannel(anyString(), anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should return error when uploading file to channel not successful`() {
        //given
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(FAILED_FILE_OBJECT))
        //when
        val testObserver =  channelsProvider.uploadFileToChannel("channel", byteArrayOf()).test()
        //then
        testObserver.assertError(Throwable::class.java)
    }
}