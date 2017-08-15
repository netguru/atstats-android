package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsController
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class SharePresenter @Inject constructor(private val channelsController: ChannelsController)
    : MvpNullObjectBasePresenter<ShareContract.View>(),
        ShareContract.Presenter {

    companion object {
        private const val CHANNEL_PREFIX = "#"
    }

    private val compositeDisposable = CompositeDisposable()

    private lateinit var currentChannelName: String

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun <T : Any> prepareView(selectedItem: T, mostActiveItemList: List<T>) {
        if (selectedItem is ChannelStatistics) {
            prepareChannelView(selectedItem, mostActiveItemList.filterIsInstance(ChannelStatistics::class.java))
        } else if (selectedItem is UserStatistic) {
            prepareUserView(selectedItem, mostActiveItemList.filterIsInstance(UserStatistic::class.java))
        } else {
            throw IllegalStateException("There is no action for selectedItem type: ${selectedItem::class.java}")
        }
    }

    override fun onSendButtonClick(screenShotByteArray: ByteArray) {
        view.showLoadingView()
        compositeDisposable += channelsController.uploadFileToChannel(currentChannelName, screenShotByteArray)
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
        view.initShareChannelView(mostActiveChannelsList)
        view.showChannelName(CHANNEL_PREFIX + selectedChannel.channelName)

        checkSelectedChannelPosition(selectedChannel, mostActiveChannelsList.last().currentPositionInList)
    }

    private fun checkSelectedChannelPosition(selectedChannel: ChannelStatistics, lastChannelPosition: Int) {
        if (isOnMostActiveItemPosition(selectedChannel.currentPositionInList, lastChannelPosition)) {
            view.showSelectedChannelMostActiveText()
        } else {
            view.showSelectedChannelTalkMoreText()
            checkShouldShowExtraChannelItem(selectedChannel, lastChannelPosition)
        }
    }

    private fun checkShouldShowExtraChannelItem(selectedChannel: ChannelStatistics, lastChannelPosition: Int) {
        if (shouldShowExtraItem(selectedChannel.currentPositionInList, lastChannelPosition)) {
            view.showSelectedChannelOnLastPosition(selectedChannel)
        }
    }

    private fun prepareUserView(selectedUser: UserStatistic, usersList: List<UserStatistic>) {
        currentChannelName = selectedUser.id
        view.initShareUsersView(usersList)
        view.showChannelName(selectedUser.name)

        checkSelectedUserPosition(selectedUser, usersList.last().currentPositionInList)
    }

    private fun checkSelectedUserPosition(selectedUser: UserStatistic, lastUserPosition: Int) {
        if (isOnMostActiveItemPosition(selectedUser.currentPositionInList, lastUserPosition)) {
            view.showSelectedUserMostActiveText()
        } else {
            view.showSelectedUserTalkMoreText()
            checkShouldShowExtraUserItem(selectedUser, lastUserPosition)
        }
    }

    private fun checkShouldShowExtraUserItem(selectedUser: UserStatistic, lastUserPosition: Int) {
        if (shouldShowExtraItem(selectedUser.currentPositionInList, lastUserPosition)) {
            view.showSelectedUserOnLastPosition(selectedUser)
        }
    }

    private fun shouldShowExtraItem(selectedItemPosition: Int, lastItemPosition: Int) = selectedItemPosition > lastItemPosition

    private fun isOnMostActiveItemPosition(selectedItemPosition: Int, lastItemPosition: Int) = selectedItemPosition == lastItemPosition
}