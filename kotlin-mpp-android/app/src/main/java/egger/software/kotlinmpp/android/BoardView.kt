package egger.software.kotlinmpp.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import egger.software.kotlinmpp.libgol.Board

class BoardView : View {

    private val paint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL
        strokeWidth = 0.0f
    }

    lateinit var board: Board

    var offsetX = 0.0f
    var offsetY = 0.0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (rowIdx in 0 until board.rows) {
            for (columnIdx in 0 until board.columns) {
                if (board.cellAt(column = columnIdx, row = rowIdx).alive) {
                    canvas.drawRect(rectFor(rowIdx, columnIdx), paint)
                }
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return zoomDetector.onTouchEvent(event)
    }

    private fun rectFor(rowIdx: Int, columnIdx: Int): RectF {
        val cellSize = board.cellSize * resources.displayMetrics.density
        val cellPadding = board.cellPadding * resources.displayMetrics.density
        val left = (columnIdx * cellSize) + cellPadding
        val right = (left + cellSize) - cellPadding
        val top = (rowIdx * cellSize) + cellPadding
        val bottom = (top + cellSize) - cellPadding

        return RectF(
            left + offsetX,
            top + offsetY,
            right + offsetX,
            bottom + offsetY
        )
    }

    private val zoomListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            val oldCellSize = board.cellSize
            board.scale(detector.scaleFactor)
            val realScaleFactor = board.cellSize / oldCellSize

            val distX = detector.focusX - offsetX
            val distY = detector.focusY - offsetY

            val corrX = distX - distX * realScaleFactor
            val corrY = distY - distY * realScaleFactor

            offsetX += corrX
            offsetY += corrY

            invalidate()
            return true
        }

    }

    private val zoomDetector: ScaleGestureDetector = ScaleGestureDetector(context, zoomListener)

}
