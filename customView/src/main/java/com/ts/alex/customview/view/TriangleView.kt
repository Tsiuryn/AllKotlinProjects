package com.ts.alex.customview.view


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.ts.alex.customview.R
import java.lang.Math.pow
import kotlin.math.acos
import kotlin.math.sqrt

class TriangleView(context: Context, attr: AttributeSet) :
    View(context, attr) {


    private var listModels = ArrayList<TriangleModel>()
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
    private var listener: ViewListener? = null

    fun setListener (listener: ViewListener){
        this.listener = listener
    }

    private val timer = HelperTimer()

    var fatCream = "Жсл"
    var fatMilk = "Жм"
    var fatSkim = "Жобм"
    var quantCream = "Ксл"
    var quantMilk = "Км"
    var quantSkim = "Кобм"



    fun updateView() {
        invalidate()
    }

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
        _rFatMilk.set(A.first.toInt() - paddingX.toInt(), (A.second-paddingY).toInt(), A.first.toInt() + paddingX.toInt(), (A.second+paddingY).toInt())



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
            _qS.first + 20f,
            _pQuantSkim
        )

        val _qC = getHeightAndWidthText(quantCream, _pQuantCream, _rQuantCream)

        canvas.drawTextOnPath(
            quantCream,
            path3,
            sizeCathetus / 2 - _qC.second / 2,
            -_qC.first.toFloat() + 20f,
            _pQuantCream
        )
        val _fC = getHeightAndWidthText(fatCream, _pFatCream, _rFatCream)
        canvas.drawText(fatCream, B.first - _fC.second / 2, B.second - 20f, _pFatCream)

        val _fM = getHeightAndWidthText(fatMilk, _pFatMilk, _rFatMilk)
        canvas.drawText(fatMilk, A.first, A.second + _fM.first + 20f, _pFatMilk)

        val _fS = getHeightAndWidthText(fatSkim, _pFatSkim, _rFatSkim)
        canvas.drawText(fatSkim, C.first - _fS.second, A.second + _fS.first + 20f, _pFatSkim)

        val numbX = (B.first - A.first).toInt() / 2
        val numbY = (C.second - B.second).toInt() / 2

        listModels.add(TriangleModel(fatCream, Coordinates(B.first.toInt(), B.second.toInt()), State.FAT_CREAM))
        canvas.drawCircle(B.first, B.second, 100f, line1)
        listModels.add(TriangleModel(fatMilk, Coordinates(A.first.toInt(), A.second.toInt()), State.FAT_MILK))
        canvas.drawCircle(A.first, A.second, 200f, line1)
        listModels.add(TriangleModel(fatSkim, Coordinates(C.first.toInt(), C.second.toInt()), State.FAT_SKIM))
        canvas.drawCircle(C.first, C.second, 200f, line1)


        listModels.add(TriangleModel(quantCream, Coordinates(B.first.toInt(), C.second.toInt()), State.QUANT_CREAM))
        canvas.drawCircle(B.first, C.second, 200f, line1)

        listModels.add(TriangleModel(quantMilk, Coordinates(B.first.toInt() + numbX, B.second.toInt() + numbY), State.QUANT_MILK))
        canvas.drawCircle((B.first + numbX), B.second + numbY, 200f, line1)

        listModels.add(TriangleModel(quantSkim, Coordinates(B.first.toInt() - numbX, B.second.toInt() + numbY), State.QUANT_SKIM))
        canvas.drawCircle(B.first - numbX, B.second + numbY, 200f, line1)
    }

    private fun getHeightAndWidthText(
        text: String,
        paint: Paint,
        rect: Rect,
    ): Pair<Float, Float> {
        paint.getTextBounds(text, 0, text.length, rect)
        val textWidth = paint.measureText(text)
        val textHeight = rect.height().toFloat()
        rect.set(0, 0, textWidth.toInt(), textHeight.toInt())
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

    private fun isIntoInCircle (coordinates: Coordinates, x: Int, y: Int, radius: Float): Boolean {
        return sqrt(pow((coordinates.coordX - x).toDouble(), 2.0) + pow((coordinates.coordY - y).toDouble(), 2.0)) < convertToDP(radius.toFloat()).toDouble()
    }

    private fun onClickText(touchX: Int, touchY: Int): Boolean {
        for (model in listModels) {
            if (isIntoInCircle(model.coord, touchX, touchY, 30f)) {

                listener?.onClick(model)
                return true
            }
        }

        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null){
            return false
        }
        val touchX = event.x.toInt()
        val touchY = event.y.toInt()
        when (event.action) {

            MotionEvent.ACTION_UP -> {
                onClickText(touchX, touchY)
            }
        }
        return true
    }
}

interface ViewListener {

    fun onClick(model: TriangleModel)


    fun onLongClick()

}


