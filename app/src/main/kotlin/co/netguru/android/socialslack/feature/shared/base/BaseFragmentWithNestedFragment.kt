package co.netguru.android.socialslack.feature.shared.base

import android.support.annotation.IdRes
import android.support.v4.app.Fragment

abstract class BaseFragmentWithNestedFragment : Fragment() {

    internal fun replaceNestedFragment(@IdRes containerId: Int, nestedFragment: Fragment) {
        childFragmentManager.beginTransaction()
                .replace(containerId, nestedFragment)
                .commit()
    }

    internal fun replaceNestedFragmentAndAddToBackStack(@IdRes containerId: Int, nestedFragment: Fragment) {
        childFragmentManager.beginTransaction()
                .replace(containerId, nestedFragment)
                .addToBackStack(null)
                .commit()
    }
}