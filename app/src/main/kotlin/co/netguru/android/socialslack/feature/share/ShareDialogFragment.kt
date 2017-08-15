package co.netguru.android.socialslack.feature.share

import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.util.ScreenShotUtils
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.share.Sharable
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.share.adapter.ShareChannelAdapter
import co.netguru.android.socialslack.feature.share.adapter.ShareUserAdapter
import co.netguru.android.socialslack.feature.share.confirmation.ShareConfirmationDialogFragment
import co.netguru.android.socialslack.feature.shared.base.BaseMvpDialogFragment
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.item_channels.view.*
import kotlinx.android.synthetic.main.item_users.view.*

class ShareDialogFragment : BaseMvpDialogFragment<ShareContract.View, ShareContract.Presenter>(),
        ShareContract.View {

    companion object {
        fun <T> newInstance(selectedItem: T, mostActiveItemList: Array<T>): ShareDialogFragment
                where T : Parcelable, T : Sharable {

            val fragment = ShareDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(SELECTED_ITEM_KEY, selectedItem)
            bundle.putParcelableArray(MOST_ACTIVE_ITEM_LIST_KEY, mostActiveItemList)

            fragment.arguments = bundle
            return fragment
        }

        val TAG: String = ShareDialogFragment::class.java.simpleName

        private const val SELECTED_ITEM_KEY = "key:selected_item"
        private const val MOST_ACTIVE_ITEM_LIST_KEY = "key:most_active_item_list"

            private const val USERNAME_PREFIX = "@"
            private const val USER_AVATAR_ROUNDED_CORNERS_MARGIN = 0

    }

    private val component by lazy {
        App.getUserComponent(context)
                .plusChannelShareComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        shareCloseBtn.setOnClickListener { presenter.onCloseButtonClick() }
        shareSendButton.setOnClickListener {
            presenter.onSendButtonClick(ScreenShotUtils.takeScreenShotByteArray(shareRootView))
        }
        presenter.prepareView(arguments.getParcelable(SELECTED_ITEM_KEY),
                arguments.getParcelableArray(MOST_ACTIVE_ITEM_LIST_KEY).toList())
    }

    override fun createPresenter() = component.getPresenter()

    override fun initShareChannelView(channelsList: List<ChannelStatistics>) {
        val adapter = ShareChannelAdapter(channelsList, ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
        shareRecyclerView.adapter = adapter
    }

    override fun initShareUsersView(userList: List<UserStatistic>) {
        val adapter = ShareUserAdapter(userList, UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST)
        shareRecyclerView.adapter = adapter
    }

    override fun showChannelName(channelName: String) {
        shareNameTextView.text = resources.getString(R.string.share_recon_this, channelName)
        shareAboutSendStatisticsTextView.text = resources.getString(R.string.share_send_statistics_to, channelName)
    }

    override fun showSelectedChannelMostActiveText() {
        shareStatusTextView.text = resources.getString(R.string.share_most_talkative_channel)
    }

    override fun showSelectedChannelTalkMoreText() {
        shareStatusTextView.text = resources.getString(R.string.share_talk_more)
    }

    override fun showSelectedChannelOnLastPosition(channelStatistics: ChannelStatistics) {
        shareMoreVertImage.visibility = View.VISIBLE
        shareLastChannelContainer.visibility = View.VISIBLE
        showLastChannelData(channelStatistics)
    }

    override fun showSelectedUserMostActiveText() {
        shareStatusTextView.text = resources.getString(R.string.share_most_talkative_user)
    }

    override fun showSelectedUserTalkMoreText() {
        shareStatusTextView.text = resources.getString(R.string.share_talk_more)
    }

    override fun showSelectedUserOnLastPosition(user: UserStatistic) {
        shareMoreVertImage.visibility = View.VISIBLE
        shareLastUserContainer.visibility = View.VISIBLE
        showLastUserData(user)
    }

    override fun showShareConfirmationDialog(itemName: String) {
        ShareConfirmationDialogFragment.newInstance(itemName).show(fragmentManager,
                ShareConfirmationDialogFragment.TAG)
        dismiss()
    }

    override fun showLoadingView() {
        shareSendButton.visibility = View.GONE
        shareLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        shareSendButton.visibility = View.VISIBLE
        shareLoadingView.visibility = View.GONE
    }

    override fun dismissView() {
        dismiss()
    }

    override fun showError() {
        Snackbar.make(shareRecyclerView, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

    private fun showLastChannelData(channelStatistics: ChannelStatistics) {
        with(channelStatistics) {
            shareLastChannel.itemChannelsPlaceNrTextView.text = (currentPositionInList.toString() + '.')
            shareLastChannel.itemChannelsNameTextView.text = channelName
            shareLastChannel.itemChannelsMessagesNrTextView.text = messageCount.toString()
        }
    }

    private fun showLastUserData(user: UserStatistic) {
        with(user) {
            shareLastUser.placeNrTextView.text = (currentPositionInList.toString() + '.')
            shareLastUser.userRealNameTextView.text = name
            shareLastUser.usernameTextView.text = (USERNAME_PREFIX + username)
            shareLastUser.messagesNrTextView.text = user.totalMessages.toString()
            loadUserPhoto(user.avatarUrl)
        }
    }

    private fun loadUserPhoto(avatarUrl: String?) {
        Glide.with(context)
                // TODO 14.08.2017 find a better placeholder
                .load(avatarUrl ?: R.drawable.this_is_totally_a_person)
                .bitmapTransform(RoundedCornersTransformation(context,
                        resources.getDimension(R.dimen.item_user_avatar_radius).toInt(), USER_AVATAR_ROUNDED_CORNERS_MARGIN))
                .into(shareLastUser.userAvatarImageView)
    }

    private fun initRecyclerView() {
        shareRecyclerView.setHasFixedSize(true)
        shareRecyclerView.addItemDecoration(DividerItemDecorator(
                context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
    }
}