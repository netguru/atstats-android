package co.netguru.android.socialslack.feature.users

import co.netguru.android.socialslack.RxSchedulersOverrideRule

import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.UsersController
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class UsersPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val filterController = mock<FilterController>()
    val usersController = mock<UsersController>()
    lateinit var view: UsersContract.View

    lateinit var usersPresenter: UsersPresenter

    @Before
    fun setUp() {
        whenever(filterController.getUsersFilterOption()).thenReturn(Single.just(UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST))

        view = mock(UsersContract.View::class.java)
        usersPresenter = UsersPresenter(usersController, filterController)
        usersPresenter.attachView(view)
    }

    @Test
    fun `should set change selected filter option when getting filter option successful`() {
        //when
        usersPresenter.getCurrentFilterOption()
        //then
        verify(view).changeSelectedFilterOption(anyObject())
    }

    @Test
    fun `should show filter option error when getting filter option failed`() {
        //given
        whenever(filterController.getUsersFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        usersPresenter.getCurrentFilterOption()
        //then
        verify(view).showFilterOptionError()
    }

    @Test
    fun `should get current user filter option when user clicked`() {
        //given
        val userPosition = 1
        //when
        usersPresenter.onUserClicked(userPosition)
        //then
        verify(filterController).getUsersFilterOption()
    }

    @Test
    fun `should show user details when user clicked and getting current filter option successful`() {
        //given
        val userPosition = 1
        //when
        usersPresenter.onUserClicked(userPosition)
        //then
        verify(view).showUserDetails(anyInt(), anyObject())
    }

    @Test
    fun `should show filter view when filter button clicked`() {
        //when
        usersPresenter.filterButtonClicked()
        //then
        verify(view).showFilterView()
    }

    @Test
    fun `should get users filter option from from filter controller when sort request received`() {
        //when
        usersPresenter.sortRequestReceived(listOf())
        //then
        verify(filterController).getUsersFilterOption()
    }

    @Test
    fun `should show sorted users list when sort request successful`() {
        //when
        usersPresenter.sortRequestReceived(listOf())
        //then
        verify(view).showUsersList(anyObject())
    }

    @Test
    fun `should show updated filter option when sort request successful`() {
        //when
        usersPresenter.sortRequestReceived(listOf())
        //then
        verify(view).changeSelectedFilterOption(anyObject())
    }

    @Test
    fun `should show filter option error when sort request failed`() {
        //given
        whenever(filterController.getUsersFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        usersPresenter.sortRequestReceived(listOf())
        //then
        verify(view).showFilterOptionError()
    }

    @Test
    fun `should show loading view when sort request received`() {
        //when
        usersPresenter.sortRequestReceived(listOf())
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should hide loading view when sort request received successful`() {
        //when
        usersPresenter.sortRequestReceived(listOf())
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should hide loading view when sort request received failed`() {
        //given
        whenever(filterController.getUsersFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        usersPresenter.sortRequestReceived(listOf())
        //then
        verify(view).hideLoadingView()
    }

    @After
    fun tearDown() {
        usersPresenter.detachView(false)
    }
}