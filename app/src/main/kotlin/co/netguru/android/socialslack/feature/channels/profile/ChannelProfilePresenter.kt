package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@FragmentScope
class ChannelProfilePresenter @Inject constructor(private val channelHistoryProvider: ChannelsProvider,
                                                  @Named("UserId") private val userId: String) :
        MvpNullObjectBasePresenter<ChannelProfileContract.View>(), ChannelProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getChannelInfo(ChannelId: String) {

        view.showLoadingView()

        compositeDisposable += channelHistoryProvider.getMessagesForChannel(ChannelId)
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = this::processData,
                        onError = {
                            view.showError()
                            view.hideLoadingView()
                            Timber.e(it, "Error while getting the total number of @here and mentions")
                        }
                )
    }

    private fun countStringAppearance(messageStream: List<ChannelMessage>, toCount: String): Single<Pair<String, Long>> {
        return Observable.fromIterable(messageStream)
                .filter { it.type == ChannelMessage.MESSAGE_TYPE && (it.text?.contains(toCount) ?: false) }
                .count()
                .map { t -> Pair(toCount, t) }
    }

    private fun showCount(map: Map<String, Long>) {
        var totalHere = map[ChannelMessage.HERE_TAG] ?: 0
        var totalMentions = map[userId] ?: 0

        view.showChannelInfo(totalHere.toInt(), totalMentions.toInt())
    }

    private fun processData(channelMessageList: List<ChannelMessage>) {
        compositeDisposable += Single.merge(
                countStringAppearance(channelMessageList, ChannelMessage.HERE_TAG)
                        .subscribeOn(Schedulers.computation()),
                // TODO 27.07.2017 should be change to the user id with the format @<userId>
                countStringAppearance(channelMessageList, userId)
                        .subscribeOn(Schedulers.computation()))
                .compose(RxTransformers.applyFlowableComputationSchedulers<Pair<String, Long>>())
                .toMap(Pair<String, Long>::first, Pair<String, Long>::second)
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = this::showCount,
                        onError = {
                            view.showError()
                            Timber.e(it, "Error while getting the total number of @here and mentions")
                        }
                )
    }

    override fun onShareButtonClick() {
        view.showShareDialogFragment()
    }
}