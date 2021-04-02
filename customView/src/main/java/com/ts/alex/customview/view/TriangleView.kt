package com.ts.alex.customview.view


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.ts.alex.customview.R
import kotlin.math.acos
import kotlin.math.sqrt

class TriangleView(context: Context, attr: AttributeSet) :
    View(context, attr) {
    private val line1 = Paint()
    private val line2 = Paint()
    private val line3 = Paint()
    private val _pFatCream = Paint()
    private val _pFatMilk = Paint()
    private val _pFatSkim = Paint()
    private val _pQuantCream = Paint()
    private val _pQuantMilk = Paint()
    private val _pQuantSkim = Paint()
    private val _rFatCream = Rect()
    private val _rFatMilk = Rect()
    private val _rFatSkim = Rect()
    private val _rQuantCream = Rect()
    private val _rQuantMilk = Rect()
    private val _rQuantSkim = Rect()
    private val textSize = 50f

    private var fatCream = "Жсл"
    private var fatMilk = "Жм"
    private var fatSkim = "Жобм"
    private var quantCream = "Ксл"
    private var quantMilk = "Км"
    private var quantSkim = "Кобм"

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

        _pFatCream.style = Paint.Style.FILL
        _pFatCream.textSize = textSize
        _pFatCream.isAntiAlias = true
        _pFatCream.color = context.getColor(R.color.line3)

        _pFatMilk.style = Paint.Style.FILL
        _pFatMilk.textSize = textSize
        _pFatMilk.isAntiAlias = true
        _pFatMilk.color = context.getColor(R.color.line2)

        _pFatSkim.style = Paint.Style.FILL
        _pFatSkim.textSize = textSize
        _pFatSkim.isAntiAlias = true
        _pFatSkim.color = context.getColor(R.color.line1)

        _pQuantCream.style = Paint.Style.FILL
        _pQuantCream.textSize = textSize
        _pQuantCream.isAntiAlias = true
        _pQuantCream.color = context.getColor(R.color.line3)

        _pQuantMilk.style = Paint.Style.FILL
        _pQuantMilk.textSize = textSize
        _pQuantMilk.isAntiAlias = true
        _pQuantMilk.color = context.getColor(R.color.line2)

        _pQuantSkim.style = Paint.Style.FILL
        _pQuantSkim.textSize = textSize
        _pQuantSkim.isAntiAlias = true
        _pQuantSkim.color = context.getColor(R.color.line1)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val _x = canvas!!.width.toFloat()
        val _y = _x
        val paddingX = convertToDP(40f)
        val paddingY = convertToDP(40f)

        val A = 0f + paddingX to _y - paddingY
        val B = _x / 2f to 0f + paddingY
        val C = _x - paddingX to _y - paddingY

        val path1 = Path()
        path1.moveTo(A.first, A.second)
        path1.lineTo(B.first, B.second)
        path1.close()

        canvas.drawPath(path1, line1)

        val path2 = Path()
        path2.moveTo(B.first, B.second)
        path2.lineTo(C.first, C.second)
        path2.close()

        canvas.drawPath(path2, line2)

        val path3 = Path()
        path3.moveTo(A.first, A.second)
        path3.lineTo(C.first, C.second)
        path3.close()

        canvas.drawPath(path3, line3)


        val sizeCathetus = getLengthLine(
            x1 = A.first,
            y1 = A.second,
            x2 = C.first,
            y2 = C.second
        )
        val sizeHypotenuse = getLengthLine(
            x1 = B.first,
            y1 = B.second,
            x2 = C.first,
            y2 = C.second
        )


        val _qM = getHeightAndWidthText(quantMilk, _pQuantMilk, _rQuantMilk)

        canvas.drawTextOnPath(
            quantMilk,
            path2,
            sizeHypotenuse / 2 - _qM.second / 2,
            _qM.first + 20f,
            _pQuantMilk
        )

        val _qS = getHeightAndWidthText(quantSkim, _pQuantSkim, _rQuantSkim)

        canvas.drawTextOnPath(
            quantSkim,
            path1,
            sizeHypotenuse / 2 - _qS.second / 2,
            _qS.first.toFloat() + 20f,
            _pQuantSkim
        )

        val _qC = getHeightAndWidthText(quantCream, _pQuantCream, _rQuantCream)

        canvas.drawTextOnPath(
            quantCream,
            path3,
            sizeCathetus / 2 - _qC.second / 2,
            - _qC.first.toFloat() + 20f,
            _pQuantCream
        )
        val _fC = getHeightAndWidthText(fatCream, _pFatCream, _rFatCream)
        canvas.drawText(fatCream, B.first - _fC.second / 2, B.second - 20f, _pFatCream)

        val _fM = getHeightAndWidthText(fatMilk, _pFatMilk, _rFatMilk)
        canvas.drawText(fatMilk, A.first, A.second + _fM.first + 20f , _pFatMilk)

        val _fS = getHeightAndWidthText(fatSkim, _pFatSkim, _rFatSkim)
        canvas.drawText(fatSkim, C.first - _fS.second, A.second + _fS.first + 20f , _pFatSkim)

    }

    private fun getHeightAndWidthText (text: String, paint: Paint, rect: Rect, ): Pair<Float, Float>{
        paint.getTextBounds(text, 0, text.length, rect)
        val textWidth = _pFatCream.measureText(text)
        val textHeight = rect.height().toFloat()
        return textHeight to textWidth
    }


    private fun getLengthLine(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt(
            Math.pow((x2 - x1).toDouble(), 2.0) + Math.pow(
                (y2 - y1).toDouble(),
                2.0
            )
        ).toFloat()
    }

    private fun getAngleHypotenuse(sizeCathetus: Float, sizeHypotenuse: Float): Float {
        val rad = acos(sizeCathetus / sizeHypotenuse)
        return rad.toFloat() * 180f / kotlin.math.PI.toFloat() + 5f
    }

    private fun convertToDP(dip: Float): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }
}

interface ViewListener {
    fun onClickFatCream()
    fun onClickFatMilk()
    fun onClickFatSkim()
    fun onClickQuantCream()
    fun onClickQuantMilk()
    fun onClickQuantSkim()

    fun onLongClickFatCream()
    fun onLongClickFatMilk()
    fun onLongClickFatSkim()
    fun onLongClickQuantCream()
    fun onLongClickQuantMilk()
    fun onLongClickQuantSkim()
}


