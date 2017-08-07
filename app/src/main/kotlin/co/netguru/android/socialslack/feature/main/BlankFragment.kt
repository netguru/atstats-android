package co.netguru.android.socialslack.feature.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.inflate

// TODO 07.07.2017 remove this fragment and the layout
class BlankFragment : Fragment() {

    companion object {
        fun newInstance() : Fragment = BlankFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_blank)
    }

}
