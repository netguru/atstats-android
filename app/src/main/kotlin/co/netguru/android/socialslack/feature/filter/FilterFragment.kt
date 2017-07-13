package co.netguru.android.socialslack.feature.filter

import android.os.Bundle
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
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

    private lateinit var component: FilterComponent

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater?.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.filterObjectTypeReceived(arguments.getSerializable(FILTER_OBJECT_TYPE) as FilterObjectType)
    }

    override fun initChannelsFilterFragment() {
        filterOption1RadioBtn.text = ChannelsFilterOption.MOST_ACTIVE_CHANNEL.value
        filterOption2RadioBtn.text = ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST.value
        filterOption3RadioBtn.text = ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE.value
    }

    override fun selectCurrentChannelFilter(currentChannelsFilterOption: ChannelsFilterOption) {
        when (currentChannelsFilterOption) {
            ChannelsFilterOption.MOST_ACTIVE_CHANNEL -> filterRadioGroup.check(filterOption1RadioBtn.id)
            ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST -> filterRadioGroup.check(filterOption2RadioBtn.id)
            ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE -> filterRadioGroup.check(filterOption3RadioBtn.id)
        }
    }

    override fun createPresenter() = component.getPresenter()

    private fun initComponent() {
        component = App.getApplicationComponent(context)
                .plusFilterComponent()
    }
}