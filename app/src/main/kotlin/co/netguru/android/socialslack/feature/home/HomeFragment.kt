package co.netguru.android.socialslack.feature.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.inflate
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

    //TODO 09.08.2017 Remove when implementing menu for this view
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        homeViewPager.adapter = homePagerAdapter
    }
}