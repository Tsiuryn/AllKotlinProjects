package alex.ts.lyfecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        const val ACTIVITY_TAG = "ATAG"
        const val FRAGMENT_TAG = "FTAG"
        fun createLog (TAG: String , message: String){
            Log.d(TAG, message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createLog(ACTIVITY_TAG, "onCreate")
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FragmentMy()).commit()
    }

    override fun onStart() {
        super.onStart()
        createLog(ACTIVITY_TAG, "OnStart")
    }

    override fun onResume() {
        super.onResume()
        createLog(ACTIVITY_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        createLog(ACTIVITY_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        createLog(ACTIVITY_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        createLog(ACTIVITY_TAG, "onDestroy")
    }

}
