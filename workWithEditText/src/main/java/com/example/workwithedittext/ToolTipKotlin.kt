package com.example.workwithedittext

import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton

class ToolTipKotlin : AppCompatImageButton {
    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    //
    //    @TargetApi(21)
    //    public ImageButtonWithToolTip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //        super(context, attrs, defStyleAttr, defStyleRes);
    //        init();
    //    }
    private fun init() {
        setOnClickListener { v ->
            /**
             * You should set the android:contentDescription attribute in this view's XML layout file.
             */
            /**
             * You should set the android:contentDescription attribute in this view's XML layout file.
             */
            val contentDescription = "Text hello"
            if (TextUtils.isEmpty(contentDescription)) {
                /**
                 * There's no content description, so do nothing.
                 */
                /**
                 * There's no content description, so do nothing.
                 */
            } else {
                val screenPos = IntArray(2) // origin is device display
                val displayFrame = Rect() // includes decorations (e.g. status bar)
                v.getLocationOnScreen(screenPos)
                v.getWindowVisibleDisplayFrame(displayFrame)
                val context = v.context
                val viewWidth = v.width
                val viewHeight = v.height
                val viewCenterX = screenPos[0] + viewWidth / 2
                val screenWidth = context.resources.displayMetrics.widthPixels
                val estimatedToastHeight = (ESTIMATED_TOAST_HEIGHT_DIPS
                        * context.resources.displayMetrics.density).toInt()
                val toolTipToast = Toast.makeText(context, contentDescription, Toast.LENGTH_SHORT)
                val showBelow = screenPos[1] < estimatedToastHeight
                if (showBelow) {
                    // Show below
                    // Offsets are after decorations (e.g. status bar) are factored in
                    toolTipToast.setGravity(
                        Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                        viewCenterX - screenWidth / 2,
                        screenPos[1] - displayFrame.top + viewHeight
                    )
                } else {
                    // Show above
                    // Offsets are after decorations (e.g. status bar) are factored in
                    // NOTE: We can't use Gravity.BOTTOM because when the keyboard is up
                    // its height isn't factored in.
                    toolTipToast.setGravity(
                        Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                        viewCenterX - screenWidth / 2,
                        screenPos[1] - displayFrame.top - estimatedToastHeight
                    )
                }
                toolTipToast.show()
            }
        }
    }

    companion object {
        private const val ESTIMATED_TOAST_HEIGHT_DIPS = 48
    }
}