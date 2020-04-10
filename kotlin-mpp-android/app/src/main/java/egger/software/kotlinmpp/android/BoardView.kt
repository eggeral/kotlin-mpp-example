package egger.software.kotlinmpp.android

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import egger.software.kotlinmpp.libgol.Board
import egger.software.kotlinmpp.libgol.Cell
import egger.software.kotlinmpp.libgol.GolCanvas

class BoardView : View {

    private val paint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL
        strokeWidth = 0.0f
    }

    lateinit var board: Board

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val golCanvas = GolCanvas()
    private val rect = RectF() // avoid object allocation in onDraw

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        golCanvas.drawBoard(
            board = board,
            drawRect = { left, right, size ->
                val deviceLeft = left * resources.displayMetrics.density
                val deviceRight = right * resources.displayMetrics.density
                val deviceSize = size * resources.displayMetrics.density
                rect.set(deviceLeft, deviceRight, deviceLeft + deviceSize, deviceRight + deviceSize)
                canvas.drawRect(rect, paint)
            }

        )

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return zoomDetector.onTouchEvent(event)
    }


    private val zoomListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            golCanvas.zoom(board, detector.scaleFactor, detector.focusX, detector.focusY)
            invalidate()
            return true
        }

    }

    private val zoomDetector: ScaleGestureDetector = ScaleGestureDetector(context, zoomListener)

}
