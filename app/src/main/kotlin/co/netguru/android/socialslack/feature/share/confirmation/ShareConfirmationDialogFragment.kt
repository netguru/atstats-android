package co.netguru.android.socialslack.feature.share.confirmation

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.extensions.inflate
import kotlinx.android.synthetic.main.fragment_share_confirmation.*

class ShareConfirmationDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(itemName: String): ShareConfirmationDialogFragment {
            val fragment = ShareConfirmationDialogFragment()
            val bundle = Bundle()
            bundle.putString(ITEM_NAME_KEY, itemName)

            fragment.arguments = bundle
            return fragment
        }

        val TAG: String = ShareConfirmationDialogFragment::class.java.simpleName

        private const val ITEM_NAME_KEY = "key:item_name"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_share_confirmation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareConfirmationCloseBtn.setOnClickListener { dismiss() }
        shareConfirmationThankYouBtn.setOnClickListener { dismiss() }
        val itemName = arguments.getString(ITEM_NAME_KEY)
        shareConfirmationItemNameText.text = itemName
        shareConfirmationThankYouText.text = resources.getString(R.string.thanks_for_sending_statistics, itemName)
    }
}