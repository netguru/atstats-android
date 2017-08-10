package co.netguru.android.socialslack.feature.filter

import android.support.annotation.VisibleForTesting
import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class FilterPresenter @Inject constructor(private val filterController: FilterController)
    : MvpNullObjectBasePresenter<FilterContract.View>(), FilterContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @VisibleForTesting
    internal lateinit var currentFilterObjectType: FilterObjectType

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun filterObjectTypeReceived(filterObjectType: FilterObjectType) {
        currentFilterObjectType = filterObjectType
        when (filterObjectType) {
            FilterObjectType.CHANNELS -> initViewWithChannelsFilterFragment()
            FilterObjectType.USERS -> initViewWithUsersFilterFragment()
        }
    }

    override fun filterOptionChanged() {
        if (currentFilterObjectType == FilterObjectType.CHANNELS) {
            saveChannelsFilterOption()
        } else {
            saveUsersFilterOption()
        }

    }

    private fun saveUsersFilterOption() {
        compositeDisposable += filterController.saveUsersFilterOption(view.getCurrentUsersFilterOption())
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribeBy(
                        onComplete = {
                            Timber.d("Filter option changed")
                            view.showMainActivityWithRequestUsersSort()
                        },
                        onError = {
                            Timber.e(it, "Error while changing users filter option")
                        }
                )
    }

    private fun saveChannelsFilterOption() {
        compositeDisposable += filterController.saveChannelsFilterOption(view.getCurrentChannelsFilterOption())
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribeBy(
                        onComplete = {
                            Timber.d("Filter option changed")
                            view.showMainActivityWithRequestChannelsSort()
                        },
                        onError = {
                            Timber.e(it, "Error while changing channels filter option")
                        }
                )
    }

    private fun initViewWithChannelsFilterFragment() {
        compositeDisposable += filterController.getChannelsFilterOption()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.initChannelsFilterFragment()
                            view.selectCurrentChannelFilter(it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting channels filter option")
                        })
    }

    private fun initViewWithUsersFilterFragment() {
        compositeDisposable += filterController.getUsersFilterOption()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.initUsersFilterFragment()
                            view.selectCurrentUsersFilter(it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting users filter option")
                        })
    }
}