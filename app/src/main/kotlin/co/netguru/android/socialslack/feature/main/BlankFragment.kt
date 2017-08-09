package co.netguru.android.socialslack.feature.main


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.inflate

// TODO 07.07.2017 remove this fragment and the layout
class BlankFragment : Fragment() {

    companion object {
        fun newInstance() : Fragment = BlankFragment()
    }

    //TODO 09.08.2017 Workaround for clearing menu options
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    //TODO 09.08.2017 Workaround for clearing menu options
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_blank)
    }

}
