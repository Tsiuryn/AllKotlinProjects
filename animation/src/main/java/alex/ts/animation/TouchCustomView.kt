package alex.ts.animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class TouchCustomView (context: Context, attr: AttributeSet): View(context, attr) {
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var listener : Listener? = null

    fun setListener (listener: Listener){
        this.listener = listener
    }

    override fun onDraw(canvas: Canvas?) {
//        val sizeX = canvas!!.width
//        val sizeY = canvas.height
//
//        val indentX: Int
//        val indentY: Int
//        if (sizeX < sizeY) {
//            indentX = 150
//            indentY = 150
//        } else  {
//            indentX = (sizeX -  (sizeY - 200)) / 2
//            indentY = 75
//        }
//
//        //рисуем прямоугольник
//        val rectangleX = sizeX - indentX * 2
//        val rectangleY = sizeY - indentY * 2
//        val rect = Rect()
//        rect.set(
//            sizeX - rectangleX - indentX  , sizeY - rectangleY - indentY
//            , rectangleX + indentX, rectangleY + indentY
//        )
//        canvas.drawRect(rect, Paint(Color.RED))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null){
            return false
        }
        val mx = event?.x
        val my = event?.y
        Log.d("TAG", "View $my - y, $mx - x")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event!!.x
                downY = event.y

                Log.d("TAG", "View $downX-downX  $downY- downY ")
                return true
            }
            MotionEvent.ACTION_UP -> {
                val upX = event!!.x
                val upY = event.y

                val deltaX = downX - upX
                val deltaY = downY - upY

                //горизонтальный свайп
//                if (Math.abs(deltaX)> MIN_DISTANCE){
                if (deltaX < 0) { //слева на право
                    listener!!.showFr1()
                    return true
                }
                if (deltaX > 0) { //справа на лево
                    listener!!.showFr2()
                    return true
                }
            }
//            }
        }
        return true
    }
}

interface Listener {
    fun showFr1()
    fun showFr2()
}