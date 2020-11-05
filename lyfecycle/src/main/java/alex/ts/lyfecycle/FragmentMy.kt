package alex.ts.lyfecycle

import alex.ts.lyfecycle.MainActivity.Companion.FRAGMENT_TAG
import alex.ts.lyfecycle.MainActivity.Companion.createLog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentMy(): Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createLog(FRAGMENT_TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createLog(FRAGMENT_TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createLog(FRAGMENT_TAG, "onCreateView")
        return inflater.inflate(R.layout.my_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createLog(FRAGMENT_TAG, "onActivityCreated")
        for (i in 0 until 1000){
            println("Hello $i")
        }

    }

    override fun onStart() {
        super.onStart()
        createLog(FRAGMENT_TAG, "onStart")

    }

    override fun onResume() {
        super.onResume()
        createLog(FRAGMENT_TAG, "onResume")

    }

    override fun onPause() {
        super.onPause()

        createLog(FRAGMENT_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(requireContext(), "BYE", Toast.LENGTH_SHORT).show()
        Log.d (FRAGMENT_TAG, "OnStop")
        createLog(FRAGMENT_TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        createLog(FRAGMENT_TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        createLog(FRAGMENT_TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        createLog(FRAGMENT_TAG, "onDetach")
    }
}