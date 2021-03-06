package co.netguru.android.atstats.feature.filter

import android.content.Context
import android.os.Bundle
import android.view.*
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.extensions.inflate
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.filter.model.FilterObjectType
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.feature.main.MainActivity
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : MvpFragment<FilterContract.View, FilterContract.Presenter>(),
        FilterContract.View {

    companion object {
        private const val FILTER_OBJECT_TYPE = "filterObjectType"

        fun newInstance(filterObjectType: FilterObjectType): FilterFragment {
            val fragment = FilterFragment()
            val bundle = Bundle()
            bundle.putString(FILTER_OBJECT_TYPE, filterObjectType.name)
            fragment.arguments = bundle

            return fragment
        }
    }

    private val component by lazy { App.getUserComponent(context).plusFilterComponent() }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_filter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.filterObjectTypeReceived(FilterObjectType.valueOf(arguments.getString(FILTER_OBJECT_TYPE)))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionApply -> {
                presenter.filterOptionChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initChannelsFilterFragment() {
        filterOption1RadioBtn.setText(ChannelsFilterOption.MOST_ACTIVE_CHANNEL.textResId)
        filterOption2RadioBtn.setText(ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST.textResId)
        filterOption3RadioBtn.setText(ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE.textResId)
        filterByTitle.setText(R.string.filter_channels_by_subtitle)
    }

    override fun selectCurrentChannelFilter(currentChannelsFilterOption: ChannelsFilterOption) {
        when (currentChannelsFilterOption) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> filterRadioGroup.check(filterOption1RadioBtn.id)
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> filterRadioGroup.check(filterOption2RadioBtn.id)
            ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> filterRadioGroup.check(filterOption3RadioBtn.id)
        }
    }

    override fun initUsersFilterFragment() {
        filterOption1RadioBtn.setText(UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST.textResId)
        filterOption2RadioBtn.setText(UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST.textResId)
        filterOption3RadioBtn.setText(UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST.textResId)
        filterByTitle.setText(R.string.filter_users_by_subtitle)
    }

    override fun selectCurrentUsersFilter(currentUsersFilterOption: UsersFilterOption) {
        when (currentUsersFilterOption) {
            UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> filterRadioGroup.check(filterOption1RadioBtn.id)
            UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> filterRadioGroup.check(filterOption2RadioBtn.id)
            UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> filterRadioGroup.check(filterOption3RadioBtn.id)
        }
    }

    override fun getCurrentChannelsFilterOption() = when (filterRadioGroup.checkedRadioButtonId) {
        filterOption1RadioBtn.id -> ChannelsFilterOption.MOST_ACTIVE_CHANNEL
        filterOption2RadioBtn.id -> ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST
        filterOption3RadioBtn.id -> ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE
        else -> throw IllegalStateException("There is no option for selected radio button")
    }

    override fun getCurrentUsersFilterOption() = when (filterRadioGroup.checkedRadioButtonId) {
        filterOption1RadioBtn.id -> UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST
        filterOption2RadioBtn.id -> UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST
        filterOption3RadioBtn.id -> UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST
        else -> throw IllegalStateException("There is no option for selected radio button")
    }

    override fun showMainActivityWithRequestChannelsSort() {
        showMainActivityWithRequest(MainActivity.REQUEST_SORT_CHANNELS)
    }

    override fun showMainActivityWithRequestUsersSort() {
        showMainActivityWithRequest(MainActivity.REQUEST_SORT_USERS)
    }

    override fun createPresenter() = component.getPresenter()

    private fun showMainActivityWithRequest(mainActivityRequest: Int) {
        MainActivity.startActivityWithRequest(context, mainActivityRequest)
        activity.finish()
    }
}