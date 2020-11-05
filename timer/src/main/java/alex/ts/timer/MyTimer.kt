package alex.ts.timer

import android.os.CountDownTimer

class MyTimer(private val timerListener: TimerListener) {
    private lateinit var timer: CountDownTimer

    fun startTimer(sec: Int, interval: Int) {
        timer = object : CountDownTimer(sec.toLong() * 1000, interval.toLong() * 1000) {

            override fun onFinish() {
                timerListener.onFinish()
            }

            override fun onTick(p0: Long) {
                timerListener.onTick()
            }
        }.start()
    }

    fun stopTimer() {
        timer.cancel()
    }

    interface TimerListener {
        fun onFinish()
        fun onTick()
    }
}