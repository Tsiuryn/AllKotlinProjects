package alex.ts.mygson

import alex.ts.mygson.adapter.ListAdapter
import alex.ts.mygson.model.MyContactModel
import alex.ts.mygson.model.MyListContacts
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListContactsFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var myAdapter: ListAdapter
    private var contactsList = ArrayList<MyContactModel>()

    companion object{
        const val PARCELABLE_LIST_CONTACT = "PARCELABLE_LIST_CONTACT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        recycler = view.findViewById(R.id.myRecycler)
        fillListContacts(55)
        createAdapter()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.exportToFile ->{
                Log.d("TAG", "onOptionsItemSelected: ")
                val myList = MyListContacts(contactsList)
                val bundle = Bundle()
                //не обязательно было делать @Parcelable класс,
                // можно было просто передать String - Json
                bundle.putParcelable(PARCELABLE_LIST_CONTACT, myList)
                val convertToFileFragment = ConvertToFileFragment()
                convertToFileFragment.arguments = bundle
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.myContainer, convertToFileFragment).addToBackStack(null).commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun fillListContacts(countOfContact: Int) {
        if(contactsList.isNotEmpty()){
            return
        }
        var date = System.currentTimeMillis()

        val periodMls = 35L * 60 * 1000
        Log.d("TAG", "fillListContacts: $date $periodMls")
        for (i in 0 until countOfContact) {
            val myId =  i
            val name = "My name_${countOfContact - myId}"
            val dateText = changeTimeToString(date, periodMls)
            date -= periodMls
            contactsList.add(MyContactModel(myId, name, dateText))
        }
    }

    private fun changeTimeToString(startTimeMls: Long, periodMls: Long): String {
        val time = startTimeMls - periodMls
        val form = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
        return form.format(time)
    }

    private fun createAdapter() {
        recycler.layoutManager = LinearLayoutManager(context)
        myAdapter = ListAdapter(contactsList)
        recycler.adapter = myAdapter
    }
}