package co.netguru.android.atstats.feature.shared.base

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import co.netguru.android.atstats.feature.main.MainActivity

abstract class BaseFragmentWithNestedFragment : Fragment() {

    internal fun replaceNestedFragment(@IdRes containerId: Int, nestedFragment: Fragment) {
        val mainActivity = activity
        if (mainActivity is MainActivity) {
            mainActivity.showMainActionBar()
        }
        childFragmentManager.beginTransaction()
                .replace(containerId, nestedFragment)
                .commit()
    }

    internal fun replaceNestedFragmentAndAddToBackStack(@IdRes containerId: Int, nestedFragment: Fragment) {
        val mainActivity = activity
        if (mainActivity is MainActivity) {
           mainActivity.showNavigationArrowInActionBar()
        }
        childFragmentManager.beginTransaction()
                .replace(containerId, nestedFragment)
                .addToBackStack(null)
                .commit()
    }
}