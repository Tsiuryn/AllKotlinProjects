package alex.ts.exampletest

import alex.ts.exampletest.holder.MyHolder
import alex.ts.exampletest.model.CountryJson
import alex.ts.exampletest.model.Example
import alex.ts.exampletest.model.MyObject
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
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

class MainViewModel : ViewModel() {
    private val URL = "http://api.worldbank.org/v2/country/"
    private val FULL_URL = "http://api.worldbank.org/v2/country/?format=json"
    private val mutableDataForListInfo = MutableLiveData<MyObject>()
    val dataForListInfo: LiveData<MyObject> = mutableDataForListInfo
    private val client = OkHttpClient()

    init {
//        getPost()
        GlobalScope.launch {
            getPost()
        }

    }
    @Throws(IOException::class)
     fun run(url: String?) {
        val request: Request = Request.Builder()
            .url(url!!)
            .build()
        OkHttpClient().newCall(request).execute().use { response ->
            val body = response.body!!.string()
            val list = JSONArray(body)
            val ob = list[1] as JSONArray
            val listCountry = ArrayList<CountryJson>()
            for (i in 0 until ob.length()){
                val myObj = ob[i] as JSONObject
                listCountry.add(
                    CountryJson(
                    myObj.getString("iso2Code"),
                    myObj.getString("name")
                )
                )
            }
            Log.d("TAG", "run: ${listCountry}")
        }
    }


    private  fun getRetrofit(URL: String): MyHolder {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(MyHolder::class.java)
    }

    private suspend fun getPost() {
        val service = getRetrofit(URL)
            val postRequest = service.getPost()
        val list = postRequest.await()
        Log.d("TAG", "onResponse: $list")


    }




}