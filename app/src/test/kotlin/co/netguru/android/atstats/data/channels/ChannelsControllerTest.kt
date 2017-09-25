package co.netguru.android.atstats.data.channels

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.channels.model.ChannelHistory
import co.netguru.android.atstats.data.channels.model.ChannelList
import co.netguru.android.atstats.data.channels.model.ChannelMessage
import co.netguru.android.atstats.data.channels.model.*
import org.junit.Assert.*
import com.nhaarman.mockito_kotlin.*
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers

@Suppress("IllegalIdentifier")
class ChannelsControllerTest {

    companion object {
        private const val CHANNEL = "channel"
        private const val LATEST = "1000"
        private const val USER = "UU22233"
        val TS: String = "1000"
        val USER_ID = "<@".plus(USER).plus(">")
        val OTHER_TYPE = "otherType"
        val OTHER_USER = "Other User"
        val MESSAGE_ONE = ChannelMessage(ChannelMessage.MESSAGE_TYPE, TS, USER, ChannelMessage.HERE_TAG)
        val MESSAGE_TWO = ChannelMessage(ChannelMessage.MESSAGE_TYPE, TS, OTHER_USER, USER_ID)
        val MESSAGE_THREE = ChannelMessage(ChannelMessage.MESSAGE_TYPE, TS, USER, ChannelMessage.HERE_TAG)
        val MESSAGE_FOUR = ChannelMessage(OTHER_TYPE, TS, USER, OTHER_USER)
        val MESSAGE_FIVE = ChannelMessage(ChannelMessage.MESSAGE_TYPE, TS, OTHER_USER, USER_ID)
        val MESSAGE_SIX = ChannelMessage(OTHER_TYPE, TS, USER, ChannelMessage.HERE_TAG)

        private val SUCCESSFUL_FILE_OBJECT = FileUploadResponse(true, "",
                FileObject("", "", "", ""))

        private val FAILED_FILE_OBJECT = FileUploadResponse(false, "no_content",
                FileObject("", "", "", ""))
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsApi = mock<ChannelsApi>()

    val channelsDao = mock<ChannelsDao>()

    val messageList: MutableList<ChannelMessage> = mutableListOf()

    lateinit var channelsController: ChannelsController

    @Before
    fun setUp() {
        val channelList = ChannelList(true, listOf())
        val channelHistory = ChannelHistory(true, listOf(ChannelMessage( ChannelMessage.MESSAGE_TYPE, LATEST, USER, USER)), false)
        whenever(channelsApi.getChannelsList()).thenReturn(Single.just(channelList))
        whenever(channelsApi.getChannelsHistory(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(Single.just(channelHistory))

        channelsController = ChannelsController(channelsApi, channelsDao)
    }

    @Test
    fun `should get channels list from api when getting channels list`() {
        //when
        val testObserver = channelsController.getChannelsList().test()
        //then
        verify(channelsApi).getChannelsList()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should upload file to api when uploading file to channel`() {
        //given
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(SUCCESSFUL_FILE_OBJECT))
        //when
        val testObserver = channelsController.uploadFileToChannel("channel", byteArrayOf()).test()
        //then
        verify(channelsApi).uploadFileToChannel(anyString(), anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should return error when uploading file to channel not successful`() {
        //given
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(FAILED_FILE_OBJECT))
        //when
        val testObserver = channelsController.uploadFileToChannel("channel", byteArrayOf()).test()
        //then
        testObserver.assertError(Throwable::class.java)
    }

    @Test
    fun `should count and store channel statistics when messages are fetch`() {
        // given
        messageList.apply {
            add(MESSAGE_ONE)
            add(MESSAGE_TWO)
            add(MESSAGE_THREE)
            add(MESSAGE_FOUR)
            add(MESSAGE_FIVE)
            add(MESSAGE_SIX)
        }
        whenever(channelsApi.getChannelsHistory(anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(Single.just(ChannelHistory(true, messageList, false)))

        // when
        val testObserver = channelsController.countChannelStatistics(CHANNEL, CHANNEL, USER).test()

        // then
        var arg: ArgumentCaptor<ChannelStatistics> = ArgumentCaptor.forClass(ChannelStatistics::class.java)
        arg?.apply {
            verify(channelsDao).insertChannel(com.nhaarman.mockito_kotlin.capture(arg))
            assertEquals(value.hereCount, 3)
            assertEquals(value.messageCount, 6)
            assertEquals(value.mentionsCount, 2)
            assertEquals(value.myMessageCount, 4)
        }
        testObserver.assertNoErrors()
    }
}