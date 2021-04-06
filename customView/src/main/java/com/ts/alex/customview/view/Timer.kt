package com.ts.alex.customview.view

import android.os.CountDownTimer
class HelperTimer(){
    private lateinit var timer: CountDownTimer

    fun startTimer (duringSec: Int, listener: () -> Unit){
        timer = object : CountDownTimer(duringSec * 1000L, 1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                listener()
            }

        }
    }

    fun stopTimer () = timer.cancel()
}

