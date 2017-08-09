package co.netguru.android.socialslack.feature.filter

import android.content.Context
import android.os.Bundle
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import co.netguru.android.socialslack.feature.main.MainActivity
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : MvpFragment<FilterContract.View, FilterContract.Presenter>(),
        FilterContract.View {

    companion object {
        private const val FILTER_OBJECT_TYPE = "filterObjectType"

        fun newInstance(filterObjectType: FilterObjectType): FilterFragment {
            val fragment = FilterFragment()
            val bundle = Bundle()
            bundle.putSerializable(FILTER_OBJECT_TYPE, filterObjectType)
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.filterObjectTypeReceived(arguments.getSerializable(FILTER_OBJECT_TYPE) as FilterObjectType)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionApply -> {
                saveCurrentFilterOption()
                MainActivity.startActivityWithRequest(context, MainActivity.REQUEST_SORT_CHANNELS)
                activity.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun initChannelsFilterFragment() {
        filterOption1RadioBtn.setText(ChannelsFilterOption.MOST_ACTIVE_CHANNEL.textResId)
        filterOption2RadioBtn.setText(ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST.textResId)
        filterOption3RadioBtn.setText(ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE.textResId)
    }

    override fun selectCurrentChannelFilter(currentChannelsFilterOption: ChannelsFilterOption) {
        when (currentChannelsFilterOption) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> filterRadioGroup.check(filterOption1RadioBtn.id)
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> filterRadioGroup.check(filterOption2RadioBtn.id)
            ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> filterRadioGroup.check(filterOption3RadioBtn.id)
        }
    }

    override fun createPresenter() = component.getPresenter()

    private fun saveCurrentFilterOption() {
        when (filterRadioGroup.checkedRadioButtonId) {
            filterOption1RadioBtn.id -> presenter.channelsFilterOptionChanged(ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
            filterOption2RadioBtn.id -> presenter.channelsFilterOptionChanged(ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST)
            filterOption3RadioBtn.id -> presenter.channelsFilterOptionChanged(ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE)
        }
    }
}