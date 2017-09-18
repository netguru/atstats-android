package co.netguru.android.socialslack.feature.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.filter.users.UsersComparator
import co.netguru.android.socialslack.data.filter.users.UsersPositionUpdater
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class UsersPresenter @Inject constructor(private val usersController: UsersController,
                                         private val filterController: FilterController)
    : MvpNullObjectBasePresenter<UsersContract.View>(), UsersContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun getCurrentFilterOption() {
        compositeDisposable += filterController.getUsersFilterOption()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.changeSelectedFilterOption(it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting current filter option")
                            view.showFilterOptionError()
                        }
                )
    }

    override fun getUsersData() {
        view.showLoadingView()
        compositeDisposable += usersController.getAllDirectChannelsStatistics()
                .map {
                    // Get only those users with messages in conversation
                    it.filter { it.messagesFromOtherUser + it.messagesFromUs > 0 }
                }
                .flatMap {
                    usersController.getAllUsersInfo(it)
                }
                .zipWith(filterController.getUsersFilterOption())
                { userList, filterOption -> Pair(userList, filterOption) }
                .flatMap { sortUsersList(it.first, it.second) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = { (userList, _) ->
                            view.showUsersList(userList)
                        },
                        onError = {
                            view.showError()
                            Timber.e(it, "Error while getting users list")
                        })
    }

    override fun onUserClicked(clickedUserPosition: Int) {
        compositeDisposable += filterController.getUsersFilterOption()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.showUserDetails(clickedUserPosition, it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting current filter option")
                        }
                )
    }

    override fun filterButtonClicked() {
        view.showFilterView()
    }

    override fun sortRequestReceived(usersList: List<UserStatistic>) {
        view.showLoadingView()
        compositeDisposable += Single.just(usersList)
                .zipWith(filterController.getUsersFilterOption())
                { userList, filterOption -> Pair(userList, filterOption) }
                .flatMap { sortUsersList(it.first, it.second) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = { (userList, filterOption) ->
                            view.showUsersList(userList)
                            view.changeSelectedFilterOption(filterOption)
                        },
                        onError = {
                            Timber.e(it, "Error while changing users order")
                            view.showFilterOptionError()
                        })
    }

    override fun searchButtonClicked() {
        view.showSearchView()
    }

    private fun sortUsersList(usersList: List<UserStatistic>,
                              usersFilterOption: UsersFilterOption
    ) = Observable.fromIterable(usersList)
            .toSortedList(UsersComparator.getUsersComparatorForFilterOption(usersFilterOption))
            .doOnSuccess { UsersPositionUpdater.updateUsersPositionInList(it, usersFilterOption) }
            .map { Pair(it, usersFilterOption) }
}