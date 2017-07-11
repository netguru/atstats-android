package com.slacksocial.android.shared.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class AutoItemScrollRecyclerView : RecyclerView {
    private var isScrollingNext: Boolean = false
    private var isScrollingBack: Boolean = false
    private var currentItem: Int = 0
    private var orientation: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        overScrollMode = View.OVER_SCROLL_NEVER
        currentItem = 0
        isNestedScrollingEnabled = false
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE && isScrollingNext) {
            // special handler to avoid displaying half elements
            scrollToNext()
        } else if (state == RecyclerView.SCROLL_STATE_IDLE && isScrollingBack) {
            scrollToBack()
        }
    }

    private fun scrollToBack() {
        val firstVisibleItemIndex = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        super.smoothScrollToPosition(firstVisibleItemIndex)
        currentItem = firstVisibleItemIndex
    }

    private fun scrollToNext() {
        val lastVisibleItemIndex = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        super.smoothScrollToPosition(lastVisibleItemIndex)
        currentItem = lastVisibleItemIndex
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        if (orientation == RecyclerView.VERTICAL && dy > 0 || orientation == RecyclerView.HORIZONTAL && dx > 0) {
            isScrollingNext = true
            isScrollingBack = false
        } else if (orientation == RecyclerView.VERTICAL && dy < 0 || orientation == RecyclerView.HORIZONTAL && dx < 0) {
            isScrollingNext = false
            isScrollingBack = true
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        //fling disabled
        return false
    }

    fun getCurrentItem(): Int {
        return if (childCount == 0) RecyclerView.NO_POSITION else currentItem
    }

    override fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        if (layoutManager is LinearLayoutManager) {
            super.setLayoutManager(layoutManager)
            orientation = layoutManager.orientation
        } else {
            throw IllegalStateException("Layout manager should be instance of LinearLayoutManager")
        }
    }
}