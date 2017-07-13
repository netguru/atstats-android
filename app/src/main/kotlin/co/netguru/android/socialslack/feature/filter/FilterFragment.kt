package co.netguru.android.socialslack.feature.filter

import android.content.Context
import android.os.Bundle
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import com.hannesdorfmann.mosby3.mvp.MvpFragment

class FilterFragment : MvpFragment<FilterContract.View, FilterContract.Presenter>(),
        FilterContract.View {

    companion object {
        fun newInstance() = FilterFragment()
    }

    private lateinit var component: FilterComponent

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater?.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_filter_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun createPresenter() = component.getPresenter()

    private fun initComponent() {
        component = App.getApplicationComponent(context)
                .plusFilterComponent()
    }
}