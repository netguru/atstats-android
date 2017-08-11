package co.netguru.android.socialslack.feature.filter

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
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
        whenever(filterController.getUsersFilterOption()).thenReturn(Single.just(UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST))

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
    fun `should save channels filter option when channels filter option changed and filter object type is channel`() {
        //given
        whenever(filterController.saveChannelsFilterOption(anyObject())).thenReturn(Completable.complete())
        filterPresenter.filterObjectTypeReceived(FilterObjectType.CHANNELS)
        //when
        filterPresenter.filterOptionChanged()
        //then
        verify(filterController).saveChannelsFilterOption(anyObject())
    }

    @Test
    fun `should show main activity with request channels sort when channels filter option changed successful and filter object type is channel`() {
        //given
        whenever(filterController.saveChannelsFilterOption(anyObject())).thenReturn(Completable.complete())
        filterPresenter.filterObjectTypeReceived(FilterObjectType.CHANNELS)
        //when
        filterPresenter.filterOptionChanged()
        //then
        verify(view).showMainActivityWithRequestChannelsSort()
    }

    @Test
    fun `should get users filter option when received user filter object`() {
        //when
        filterPresenter.filterObjectTypeReceived(FilterObjectType.USERS)
        //then
        verify(filterController).getUsersFilterOption()
    }

    @Test
    fun `should init users filter view when getting users filter option successful`() {
        //when
        filterPresenter.filterObjectTypeReceived(FilterObjectType.USERS)
        //then
        verify(view).initUsersFilterFragment()
    }

    @Test
    fun `should select current users filter option when getting users filter option successful`() {
        //when
        filterPresenter.filterObjectTypeReceived(FilterObjectType.USERS)
        //then
        verify(view).selectCurrentUsersFilter(anyObject())
    }

    @Test
    fun `should save users filter option when users filter option changed and current filter object type is user`() {
        //given
        whenever(filterController.saveUsersFilterOption(anyObject())).thenReturn(Completable.complete())
        filterPresenter.filterObjectTypeReceived(FilterObjectType.USERS)
        //when
        filterPresenter.filterOptionChanged()
        //then
        verify(filterController).saveUsersFilterOption(anyObject())
    }

    @Test
    fun `should show main activity with request users sort when users filter option changed successful and filter object type is user`() {
        //given
        whenever(filterController.saveUsersFilterOption(anyObject())).thenReturn(Completable.complete())
        filterPresenter.filterObjectTypeReceived(FilterObjectType.USERS)
        //when
        filterPresenter.filterOptionChanged()
        //then
        verify(view).showMainActivityWithRequestUsersSort()
    }

    @After
    fun tearDown() {
        filterPresenter.detachView(false)
    }
}