package co.netguru.android.atstats.feature.share

import co.netguru.android.atstats.app.scope.FragmentScope
import co.netguru.android.atstats.common.util.RxTransformers
import co.netguru.android.atstats.data.channels.ChannelsController
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

import javax.inject.Inject

//TODO 16.08.2017 Should be divided into two presenters

@FragmentScope
class SharePresenter @Inject constructor(private val channelsController: ChannelsController)
    : MvpNullObjectBasePresenter<ShareContract.View>(),
        ShareContract.Presenter {

    companion object {
        private const val CHANNEL_PREFIX = "#"
        private const val POSITION_FIRST = 1
    }

    private val compositeDisposable = CompositeDisposable()

    private var channelId: String = ""
    private var channelName: String = ""

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun <T : Any> prepareView(selectedItem: T, mostActiveItemList: List<T>, filterName: String) {
        when (selectedItem) {
            is ChannelStatistics -> prepareChannelView(selectedItem,
                    mostActiveItemList.filterIsInstance(ChannelStatistics::class.java),
                    ChannelsFilterOption.valueOf(filterName))
            is UserStatistic -> prepareUserView(selectedItem,
                    mostActiveItemList.filterIsInstance(UserStatistic::class.java),
                    UsersFilterOption.valueOf(filterName))
            else -> throw IllegalStateException("There is no action for selectedItem type: ${selectedItem::class.java}")
        }
    }

    override fun onSendButtonClick(screenShotByteArray: ByteArray) {
        view.showLoadingView()
        compositeDisposable += channelsController.uploadFileToChannel(channelId, screenShotByteArray)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .doAfterTerminate(view::hideLoadingView)
                .subscribeBy(
                        onComplete = {
                            Timber.d("Screenshot sent to $channelName")
                            view.showShareConfirmationDialog(channelName)
                        },
                        onError = {
                            Timber.e(it, "Error while uploading screenshot to server")
                            view.showError()
                        })
    }

    override fun onCloseButtonClick() {
        view.dismissView()
    }

    private fun prepareChannelView(selectedChannel: ChannelStatistics, mostActiveChannelsList: List<ChannelStatistics>,
                                   filterOption: ChannelsFilterOption) {
        channelId = selectedChannel.channelId
        channelName = CHANNEL_PREFIX + selectedChannel.channelName
        view.initShareChannelView(mostActiveChannelsList, filterOption)
        view.showName(CHANNEL_PREFIX + selectedChannel.channelName, true)

        prepareChannelNrTitleView(filterOption)
        checkSelectedChannelPosition(selectedChannel, mostActiveChannelsList.last().currentPositionInList, filterOption)
    }

    private fun prepareChannelNrTitleView(filterOption: ChannelsFilterOption) {
        when (filterOption) {
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> view.showMentionsNrTitle()
            else -> view.showMessagesNrTitle()
        }
    }

    private fun checkSelectedChannelPosition(selectedChannel: ChannelStatistics, lastChannelPosition: Int, filterOption: ChannelsFilterOption) {
        if (isOnMostActiveItemPosition(selectedChannel.currentPositionInList)) {
            view.showSelectedChannelMostActiveText()
        } else {
            view.showSelectedChannelTalkMoreText()
            checkShouldShowExtraChannelItem(selectedChannel, lastChannelPosition, filterOption)
        }
    }

    private fun checkShouldShowExtraChannelItem(selectedChannel: ChannelStatistics, lastChannelPosition: Int, filterOption: ChannelsFilterOption) {
        if (shouldShowExtraItem(selectedChannel.currentPositionInList, lastChannelPosition)) {
            view.showSelectedChannelOnLastPosition(selectedChannel, filterOption)
        }
    }

    private fun prepareUserView(selectedUser: UserStatistic, usersList: List<UserStatistic>, filterOption: UsersFilterOption) {
        channelId = selectedUser.id
        channelName = selectedUser.name
        view.showMessagesNrTitle()
        view.initShareUsersView(usersList, filterOption)
        view.showName(selectedUser.name, false)

        checkSelectedUserPosition(selectedUser, usersList.last().currentPositionInList, filterOption)
    }

    private fun checkSelectedUserPosition(selectedUser: UserStatistic, lastUserPosition: Int, filterOption: UsersFilterOption) {
        if (isOnMostActiveItemPosition(selectedUser.currentPositionInList)) {
            view.showSelectedUserMostActiveText()
        } else {
            view.showSelectedUserTalkMoreText()
            checkShouldShowExtraUserItem(selectedUser, lastUserPosition, filterOption)
        }
    }

    private fun checkShouldShowExtraUserItem(selectedUser: UserStatistic, lastUserPosition: Int, filterOption: UsersFilterOption) {
        if (shouldShowExtraItem(selectedUser.currentPositionInList, lastUserPosition)) {
            view.showSelectedUserOnLastPosition(selectedUser, filterOption)
        }
    }

    private fun shouldShowExtraItem(selectedItemPosition: Int, lastItemPosition: Int) = selectedItemPosition > lastItemPosition

    private fun isOnMostActiveItemPosition(selectedItemPosition: Int) = selectedItemPosition == POSITION_FIRST
}