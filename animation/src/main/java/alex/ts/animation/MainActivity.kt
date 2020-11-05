package alex.ts.animation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()

{
    private lateinit var tv: TextView
    private var  MIN_DISTANCE: Int = 0
    private var downX = 0f
    private var downY = 0f
    private var frag = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG", "onCreate")
        val dm = resources.displayMetrics
        MIN_DISTANCE = (120f * dm.densityDpi / 160f + 0.5).toInt()
        showFragment1()

    }

    private fun showFragment1() {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.set_left_in, R.anim.set_right_out,
            R.anim.set_right_in, R.anim.set_left_out).replace(R.id.fragment_container, Fragment1()).commit()
        frag = 1
    }
    private fun showFragment2(isFromTheFirst: Boolean) {
        if (isFromTheFirst){
            supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.set_right_in, R.anim.set_left_out,
                R.anim.set_left_in, R.anim.set_right_out).replace(R.id.fragment_container, Fragment2()).commit()
        }else supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.set_left_in, R.anim.set_right_out,
            R.anim.set_right_in, R.anim.set_left_out).replace(R.id.fragment_container, Fragment2()).commit()

        frag = 2
    }
    private fun showFragment3() {
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.set_right_in, R.anim.set_left_out,
            R.anim.set_left_in, R.anim.set_right_out).replace(R.id.fragment_container, Fragment3()).commit()
        frag = 3
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
            if(event == null){
                return false
            }
        val mx = event.x
        val my = event.y
        Log.d ("TAG", "onTouchActivity $my - y, $mx - x")

        when (event!!.action){
            MotionEvent.ACTION_DOWN ->{
                downX = event.x
                downY = event.y

                Log.d ("TAG", "$downX-downX  $downY- downY ")
                return true
            }
            MotionEvent.ACTION_UP ->{
                val upX = event!!.x
                val upY = event.y

                val deltaX = downX - upX
                val deltaY = downY - upY

                //горизонтальный свайп
                if (Math.abs(deltaX)> MIN_DISTANCE){
                if (deltaX < 0){ //слева на право
                    when(frag){
                        2 ->{
                            this.showFragment1()
                            return true
                        }
                        3 ->{
                            this.showFragment2(false)
                            return true
                        }

                    }
                    return false
                }
                if (deltaX > 0 ){ //справа на лево
                    when(frag){
                        1 ->{
                            this.showFragment2(true)
                            return true
                        }
                        2 ->{
                            this.showFragment3()
                            return true
                        }

                    }
                    return false
                }
            }
            }
        }
        return true
    }


}
