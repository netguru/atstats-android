package co.netguru.android.socialslack.feature.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class HomeAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = HomeScreen.getScreenAtPosition(position).newInstance()

    override fun getCount(): Int {
        return HomeScreen.values().size
    }
}