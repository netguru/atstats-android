package co.netguru.android.atstats.feature.channels.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.feature.channels.ChannelsFragment
import co.netguru.android.atstats.feature.shared.base.BaseFragmentWithNestedFragment

class ChannelsRootFragment : BaseFragmentWithNestedFragment() {

    companion object {
        fun newInstance() = ChannelsRootFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_channels_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replaceNestedFragment(R.id.fragmentChannelRootContainer, ChannelsFragment.newInstance())
    }

    fun sortChannelsData() {
        val fragment = childFragmentManager.findFragmentById(R.id.fragmentChannelRootContainer)
        if (fragment is ChannelsFragment) {
            fragment.sortData()
        }
    }
}