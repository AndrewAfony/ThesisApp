package andrewafony.thesis.application.feature_home.presentation.adapter

import android.content.res.Resources
import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class TopPullIndicatorDecorator: ItemDecoration() {

    private val indicatorWidth = 50.dpToPx()
    private val indicatorHeight = 2.dpToPx()
    private val indicatorColor = Color.GRAY
    private val cornerRadius = 8.dpToPx().toFloat()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = indicatorColor
        style = Paint.Style.FILL
        alpha = 128
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = indicatorHeight + 16
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val top = 16f
        val bottom = top + indicatorHeight + 8
        val centerX = parent.width / 2f

        val left = centerX - indicatorWidth / 2f
        val right = centerX + indicatorWidth / 2f

        val rect = RectF(left, top, right, bottom)
        c.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
    }

    private fun Int.dpToPx(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (this * density).toInt()
    }
}