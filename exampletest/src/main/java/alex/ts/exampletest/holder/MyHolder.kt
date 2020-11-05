package alex.ts.exampletest.holder

import alex.ts.exampletest.model.Example
import alex.ts.exampletest.model.MyObject
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET

interface MyHolder {
    @GET("?format=json")
    fun getPost(): Deferred<List<Example>>
}