package by.st.generatorforms.recyclerpayload

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.st.generatorforms.recyclerpayload.adapter.MyAdapter
import by.st.generatorforms.recyclerpayload.entity.MyEntity

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var vm: MainViewModel
    private var myList = ArrayList<MyEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.MyRecycler)
        createRecycler(createList())
        vm = ViewModelProvider(this).get(MainViewModel::class.java)

        observeViewModel()
        vm.startToUpdate()
    }

    private fun observeViewModel() {
        vm.updateData.observe(this, Observer {
            Log.d("TAG", "observeViewModel")
            updateAdapter()
        })
    }

    private fun createRecycler(myList: MutableList<MyEntity>) {
        recycler.layoutManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(myList)
        recycler.adapter = myAdapter

    }

    private fun createList(): MutableList<MyEntity> {
        for (i in 0 until 20) {
            myList.add(MyEntity(i, "Name is $i"))
        }
        return myList
    }

    private fun updateAdapter() {
        val randomList = ArrayList<Int>()
        for (i in 0 until 6){
            val rand = (Math.random()*21-1).toInt()
            randomList.add(rand)
        }
        for (i in 0 until 20) {
            for (j in 0 until 6){
                if (i == randomList[j]){
                    val rand = (Math.random()*100).toInt()
                    myList[i] = MyEntity(i, "Name is $rand")
                }
            }
        }
        myAdapter.updateAdapter(myList)

    }
}
