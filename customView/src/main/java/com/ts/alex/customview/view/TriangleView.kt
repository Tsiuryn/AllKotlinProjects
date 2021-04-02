package com.ts.alex.customview.view


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.ts.alex.customview.R

class TriangleView(context: Context, attr: AttributeSet) : View(context, attr) {
    private val line1 = Paint()
    private val line2 = Paint()
    private val line3 = Paint()
    private val text = Paint()
    init {
        line1.style = Paint.Style.STROKE
        line1.strokeWidth = 10f
        line1.color = context.getColor(R.color.line1)

        line2.style = Paint.Style.STROKE
        line2.strokeWidth = 10f
        line2.color = context.getColor(R.color.line2)

        line3.style = Paint.Style.STROKE
        line3.strokeWidth = 10f
        line3.color = context.getColor(R.color.line3)
    }




    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val _x = canvas!!.width.toFloat()
        val _y = _x
        val paddingX = convertToDP(40f)
        val paddingY = convertToDP(40f)

        val path1 = Path()
        path1.moveTo(0f + paddingX, _y - paddingY)
        path1.lineTo(_x / 2f , 0f + paddingY )
        path1.close()

        canvas.drawPath(path1, line1)

        val path2 = Path()
        path2.moveTo(_x / 2f , 0f + paddingY )
        path2.lineTo(_x - paddingX, _y - paddingY )
        path2.close()

        canvas.drawPath(path2, line2)

        val path3 = Path()
        path3.moveTo(_x - paddingX, _y - paddingY )
        path3.lineTo(0f + paddingX, _y - paddingY)
        path3.close()

        canvas.drawPath(path3, line3)

        val path4 = Path()
        path4.moveTo(_x / 2f , 0f + paddingY )
        path4.lineTo(_x / 2f , _y - paddingY )
        path4.close()

        canvas.drawPath(path4, line2)

        val path5 = Path()
        path5.moveTo(0f , _y / 2f)
        path5.lineTo(_x, _y / 2f )
        path5.close()

        canvas.drawPath(path5, line2)

        val mainText = "Hello"
        text.style = Paint.Style.FILL
        text.textSize = 70f
        text.isAntiAlias = true
        val rect = Rect()
        text.getTextBounds(mainText, 0, mainText.length, rect)
        val textWidth = text.measureText(mainText)
        val textHeight = rect.height()

        canvas.drawText(mainText, _x / 2f - textWidth / 2f, _y / 2f + textHeight/2f, text)


    }

    private fun  convertToDP(dip: Float): Float{
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }
}


