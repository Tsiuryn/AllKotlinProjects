package alex.ts.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity(), MyTimer.TimerListener {
    private val myTimer = MyTimer(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myTimer.startTimer(20, 1)
    }

    override fun onFinish() {
        Log.d("TAG", "onFinish: ")
        myTimer.stopTimer()
        myTimer.startTimer(20, 1)
    }

    override fun onTick() {
        Log.d("TAG", "onTick: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        myTimer.stopTimer()
    }
}
