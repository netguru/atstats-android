package co.netguru.android.atstats.feature.shared.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View

class DividerItemDecorator(context: Context, private val orientation: Orientation,
                           private val drawDividerAfterLastItem: Boolean) : RecyclerView.ItemDecoration() {

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    enum class Orientation {
        HORIZONTAL_LIST, VERTICAL_LIST
    }

    private val divider: Drawable

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == Orientation.VERTICAL_LIST) {
            drawVertical(canvas, parent)
        } else {
            drawHorizontal(canvas, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == Orientation.VERTICAL_LIST) {
            outRect.set(0, 0, 0, divider.intrinsicHeight)
        } else {
            outRect.set(0, 0, divider.intrinsicWidth, 0)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (i != childCount - 1 || drawDividerAfterLastItem) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin + Math.round(child.translationY)
                val bottom = top + divider.intrinsicHeight

                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (i != childCount - 1 || drawDividerAfterLastItem) {

                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val left = child.right + params.rightMargin + Math.round(child.translationX)
                val right = left + divider.intrinsicHeight

                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }
    }
}