package co.netguru.android.atstats.feature.shared.base

import android.content.Context
import android.support.annotation.MenuRes
import android.view.Menu
import android.view.MenuInflater
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class BaseMvpFragmentWithMenu<V : MvpView, P : MvpPresenter<V>> : MvpFragment<V, P>() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(getMenuResource(), menu)
    }

    @MenuRes
    abstract internal fun getMenuResource(): Int
}