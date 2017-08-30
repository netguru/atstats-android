package co.netguru.android.socialslack.feature.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.common.extensions.startActivity
import co.netguru.android.socialslack.feature.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val homePagerAdapter by lazy { HomeAdapter(fragmentManager) }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_search, menu)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionSearch -> {
            context.startActivity<SearchActivity>()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initViewPager() {
        homeViewPager.adapter = homePagerAdapter
    }
}