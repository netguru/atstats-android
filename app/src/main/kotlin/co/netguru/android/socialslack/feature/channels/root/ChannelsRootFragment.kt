package co.netguru.android.socialslack.feature.channels.root


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.feature.channels.ChannelsFragment

class ChannelsRootFragment : Fragment() {

    companion object {
        fun newInstance() = ChannelsRootFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.fragment_channels_root, container, false)

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragmentChannelRootContainer, ChannelsFragment())
                .commit()

        return view
    }

}
