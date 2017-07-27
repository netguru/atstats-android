package co.netguru.android.socialslack.feature.share.confirmation

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R

class ShareConfirmationDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = ShareConfirmationDialogFragment()

        val TAG: String = ShareConfirmationDialogFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_share_confirmation, container, false)
    }
}