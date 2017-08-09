package co.netguru.android.socialslack.feature.filter

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
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

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun filterObjectTypeReceived(filterObjectType: FilterObjectType) {
        when (filterObjectType) {
            FilterObjectType.CHANNELS -> initViewWithChannelsFilterFragment()
            else -> Timber.d("Received users to filter")
        }
    }

    override fun channelsFilterOptionChanged(channelsFilterOption: ChannelsFilterOption) {
        compositeDisposable += filterController.saveChannelsFilterOption(channelsFilterOption)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribeBy(
                        onComplete = {
                            Timber.d("Filter option changed to $channelsFilterOption")
                        },
                        onError = {
                            Timber.e(it, "Error while changing filter option")
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
                            Timber.e(it, "Error while getting channels from server")
                        })
    }
}