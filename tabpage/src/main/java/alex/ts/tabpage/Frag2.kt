package alex.ts.tabpage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag2_fr.*
import java.lang.ClassCastException

class Frag2: Fragment() {

    private lateinit var myCallback: MyCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag2_fr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener{
            Log.d("TAG", "onViewCreated button")
            myCallback.sendToActivity()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            myCallback = context as MyCallback
        }catch (e: ClassCastException){}
    }

    interface MyCallback{
        fun sendToActivity()
    }
}

