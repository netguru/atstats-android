package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.direct.model.*
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString

@Suppress("IllegalIdentifier")
class DirectChannelsControllerTest {

    companion object {
        private const val CHANNEL = "channel"
        private const val MOCKED_TIMESTAMP = 1506031200
        private const val HOURS_24_IN_SECONDS = 60 * 60 * 24
        private const val CREATED = 0L
        private const val USER = "UU22233"
        private const val USER_WRITES_TO_US_MOST = "user1"
        private const val USER_WE_WRITE_MOST = "user2"
        private const val USER_WE_TALK_THE_MOST = "user3"
        private const val RANDOM_USER = "user4"
        //
        private val DIRECT_CHANNEL_1 = DirectChannel("1", USER_WRITES_TO_US_MOST, CREATED, false)
        private val DIRECT_CHANNEL_2 = DirectChannel("2", USER_WE_WRITE_MOST, CREATED, false)
        private val DIRECT_CHANNEL_3 = DirectChannel("3", USER_WE_TALK_THE_MOST, CREATED, false)
        private val DIRECT_CHANNEL_4 = DirectChannel("4", RANDOM_USER, CREATED, false)
        //
        //message from today
        private val MESSAGE_1 = DirectMessage(USER_WE_TALK_THE_MOST, "", MOCKED_TIMESTAMP.toString())
        // message from yesterday
        private val MESSAGE_2 = DirectMessage(USER, "", (MOCKED_TIMESTAMP - HOURS_24_IN_SECONDS).toString())
        //message from 4 days before
        private val MESSAGE_3 = DirectMessage(USER_WE_TALK_THE_MOST, "", (MOCKED_TIMESTAMP - 4 * HOURS_24_IN_SECONDS).toString())
        //message from 5 days before
        private val MESSAGE_4 = DirectMessage(USER, "", (MOCKED_TIMESTAMP - 5 * HOURS_24_IN_SECONDS).toString())
        //message from 5 days before
        private val MESSAGE_44 = DirectMessage(USER_WE_TALK_THE_MOST, "", (MOCKED_TIMESTAMP - 5 * HOURS_24_IN_SECONDS).toString())
        //message from 6 days before
        private val MESSAGE_5 = DirectMessage(USER_WE_TALK_THE_MOST, "", (MOCKED_TIMESTAMP - 6 * HOURS_24_IN_SECONDS).toString())
        //message from 7 days before
        private val MESSAGE_6 = DirectMessage(USER_WE_TALK_THE_MOST, "", (MOCKED_TIMESTAMP - 7 * HOURS_24_IN_SECONDS).toString())
        //
        private val LIST_CHANNELS = listOf(DIRECT_CHANNEL_1, DIRECT_CHANNEL_2, DIRECT_CHANNEL_3, DIRECT_CHANNEL_4)
        private val DIRECT_CHANNEL_LIST = DirectChannelList(true, LIST_CHANNELS)
        private val LIST_MESSAGES_USER_WE_TALK_MORE = listOf(MESSAGE_1, MESSAGE_2, MESSAGE_3, MESSAGE_4, MESSAGE_44, MESSAGE_5, MESSAGE_6)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val directChannelsApi = mock<DirectChannnelsApi>()

    val directChannelsDao = mock<DirectChannelsDao>()

    lateinit var directChannelsController: DirectChannelsController

    @Before
    fun setUp() {
        directChannelsController = DirectChannelsController(directChannelsApi, directChannelsDao)
    }

    @Test
    fun `should get the list of direct channels when getting direct channel list`() {
        // given
        whenever(directChannelsApi.getDirectMessagesList()).thenReturn(Single.just(DIRECT_CHANNEL_LIST))
        //when
        val testObserver = directChannelsController.getDirectChannelsList().test()
        //then
        verify(directChannelsApi).getDirectMessagesList()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should show error when getting error from direct channel list`() {
        // given
        val throwable = Throwable()
        whenever(directChannelsApi.getDirectMessagesList()).thenReturn(Single.error(throwable))
        // when
        val testObserver = directChannelsController.getDirectChannelsList().test()
        // then
        verify(directChannelsApi).getDirectMessagesList()
        testObserver.assertError(throwable)
    }

    @Test
    fun `should count and store direct messages when messages are fetch`() {
        // given
        whenever(directChannelsApi.getDirectMessagesWithUser(anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(Single.just(DirectChannelsHistory(true, MOCKED_TIMESTAMP.toString(), LIST_MESSAGES_USER_WE_TALK_MORE, false)))
        // when
        val testObserver = directChannelsController.countDirectChannelStatistics(CHANNEL, USER_WE_TALK_THE_MOST, false).test()
        // then
        val arg: ArgumentCaptor<DirectChannelStatistics> = ArgumentCaptor.forClass(DirectChannelStatistics::class.java)
        arg.run {
            verify(directChannelsDao).insertDirectChannel(com.nhaarman.mockito_kotlin.capture(arg))
            assertEquals(5, value.messagesFromOtherUser)
            assertEquals(2, value.messagesFromUs)
        }
        testObserver.assertNoErrors()
    }

    @Test
    fun `should get maximum days streak properly`() {
        // given
        whenever(directChannelsApi.getDirectMessagesWithUser(anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(Single.just(DirectChannelsHistory(true, MOCKED_TIMESTAMP.toString(), LIST_MESSAGES_USER_WE_TALK_MORE, false)))
        // when
        val testObserver = directChannelsController.countDirectChannelStatistics(CHANNEL, USER_WE_TALK_THE_MOST, false).test()
        // then
        val arg: ArgumentCaptor<DirectChannelStatistics> = ArgumentCaptor.forClass(DirectChannelStatistics::class.java)
        arg.run {
            verify(directChannelsDao).insertDirectChannel(com.nhaarman.mockito_kotlin.capture(arg))
            assertEquals(4, value.streakDays)
        }
        testObserver.assertNoErrors()
    }
}