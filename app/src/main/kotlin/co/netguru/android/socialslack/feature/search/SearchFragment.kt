package co.netguru.android.socialslack.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.inflate
import com.hannesdorfmann.mosby3.mvp.MvpFragment

class SearchFragment : MvpFragment<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val component by lazy {
        App.getUserComponent(context).plusSearchComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_search)
    }

    override fun createPresenter() = component.getPresenter()
}