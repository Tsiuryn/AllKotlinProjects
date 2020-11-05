package alex.ts.recyclerfilter

import alex.ts.recyclerfilter.adapter.MyAdapter
import alex.ts.recyclerfilter.model.MyModel
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment: Fragment() {
    private val myList = ArrayList<MyModel>()
    private lateinit var adapter : MyAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.adapterList)
        createAdapter()
        btnFrag.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, SecondFragment()).addToBackStack("").commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as android.widget.SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                Toast.makeText(requireContext(), "$newText", Toast.LENGTH_SHORT).show()
                return false
            }

        })
    }

    private fun createAdapter (){
        fillList(500)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MyAdapter(myList)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun fillList(qntItem: Int) {
        for (i in 0 until qntItem){
            val name = "Name: ${getNumber(100)}"
            myList.add(MyModel(i, name, getNumber(1000000)))
        }
    }
    private fun getNumber (up: Int): Int{
        return (Math.random()*up).toInt()
    }
}