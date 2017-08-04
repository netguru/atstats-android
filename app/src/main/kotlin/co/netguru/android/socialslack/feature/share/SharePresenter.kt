package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class SharePresenter @Inject constructor(private val channelsProvider: ChannelsProvider)
    : MvpNullObjectBasePresenter<ShareContract.View>(),
        ShareContract.Presenter {

    companion object {
        private const val CHANNEL_PREFIX = "#"
        private const val MOST_ACTIVE_ITEM_POSITION = 1
        private const val NUMBER_OF_MOST_ACTIVE_ITEMS = 3
    }

    private val compositeDisposable = CompositeDisposable()

    private lateinit var currentChannelName: String

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun <T> prepareView(selectedItem: T, mostActiveItemList: List<T>) {
        if (selectedItem is ChannelStatistics) {
            prepareChannelView(selectedItem, mostActiveItemList.filterIsInstance(ChannelStatistics::class.java))
        }
    }

    override fun onSendButtonClick(screenShotByteArray: ByteArray) {
        view.showLoadingView()
        compositeDisposable += channelsProvider.uploadFileToChannel(currentChannelName, screenShotByteArray)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .doAfterTerminate(view::hideLoadingView)
                .subscribeBy(
                        onComplete = {
                            Timber.d("Screenshot sent to $currentChannelName")
                            view.showShareConfirmationDialog(CHANNEL_PREFIX + currentChannelName)
                        },
                        onError = {
                            Timber.e(it, "Error while uploading screenshot to server")
                            view.showError()
                        })
    }

    override fun onCloseButtonClick() {
        view.dismissView()
    }

    private fun prepareChannelView(selectedChannel: ChannelStatistics, mostActiveChannelsList: List<ChannelStatistics>) {
        currentChannelName = selectedChannel.channelName
        view.initShareChannelView(selectedChannel, mostActiveChannelsList)
        view.showChannelName(CHANNEL_PREFIX + selectedChannel.channelName)

        val lastMostActiveItem = mostActiveChannelsList[NUMBER_OF_MOST_ACTIVE_ITEMS - 1]
        checkSelectedChannelPosition(selectedChannel, lastMostActiveItem.currentPositionInList)
    }

    private fun checkSelectedChannelPosition(selectedChannel: ChannelStatistics, lastMostActiveChannelPosition: Int) {
        if (selectedChannel.currentPositionInList == MOST_ACTIVE_ITEM_POSITION) {
            view.showSelectedChannelMostActiveText()
        } else {
            view.showSelectedChannelTalkMoreText()
            checkShouldShowExtraItem(selectedChannel, lastMostActiveChannelPosition)
        }
    }

    private fun checkShouldShowExtraItem(selectedChannel: ChannelStatistics, lastMostActiveChannelPosition: Int) {
        if (selectedChannel.currentPositionInList > lastMostActiveChannelPosition) {
            view.showSelectedChannelOnLastPosition(selectedChannel)
        }
    }
}