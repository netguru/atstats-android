package co.netguru.android.atstats.feature.shared.base

import android.support.v4.app.DialogFragment
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback
import android.os.Bundle
import android.app.Activity
import android.view.View

abstract class BaseMvpDialogFragment<V : MvpView, P : MvpPresenter<V>> : DialogFragment(),
        MvpDelegateCallback<V, P> {

    private val mvpDelegate by lazy { FragmentMvpDelegateImpl(this, this, true, true) }

    private lateinit var  presenter: P

    override fun setPresenter(presenter: P) {
       this.presenter = presenter
    }

    override fun getPresenter(): P = presenter

    @Suppress("UNCHECKED_CAST")
    override fun getMvpView(): V {
        return this as V
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mvpDelegate.onAttach(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpDelegate.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onStart()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        mvpDelegate.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        mvpDelegate.onDetach()
    }
}