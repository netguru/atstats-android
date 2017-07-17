package co.netguru.android.socialslack.feature.channels.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R


class ChannelProfileFragment : Fragment() {

    companion object {
        fun newInstance () = ChannelProfileFragment()
        val TAG:String = ChannelProfileFragment::class.java.canonicalName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_channel_profile, container, false)
    }

}
