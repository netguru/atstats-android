package co.netguru.android.socialslack.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.feature.search.adapter.SearchItemType
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : MvpFragment<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {

    companion object {
        fun newInstance(searchItemType: SearchItemType):SearchFragment {
            val bundle = Bundle()
            bundle.putString(SEARCH_ITEM_TYPE, searchItemType.name)

            val searchFragment = SearchFragment()
            searchFragment.arguments = bundle

            return searchFragment
        }

        private const val SEARCH_ITEM_TYPE = "key:search_item_type"
    }

    private val component by lazy {
        App.getUserComponent(context).plusSearchComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = container?.inflate(R.layout.fragment_search)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchText.text = SearchItemType.valueOf(arguments.getString(SEARCH_ITEM_TYPE)).name
    }

    override fun createPresenter() = component.getPresenter()
}