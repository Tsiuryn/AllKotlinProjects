package alex.ts.exampletest

import alex.ts.exampletest.holder.MyHolder
import alex.ts.exampletest.model.CountryJson
import alex.ts.exampletest.model.MyObject
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity() {
    private val FULL_URL = "http://api.worldbank.org/v2/country/?format=json"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.dataForListInfo.observe(this, androidx.lifecycle.Observer {
            Log.d("TAG", "onCreate: $it")
        })
//        GlobalScope.launch {
//            run(FULL_URL)
//        }
    }




}