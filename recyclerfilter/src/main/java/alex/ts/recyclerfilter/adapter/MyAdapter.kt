package alex.ts.recyclerfilter.adapter

import alex.ts.recyclerfilter.R
import alex.ts.recyclerfilter.model.MyModel
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(private var itemList: ArrayList<MyModel>) :
    RecyclerView.Adapter<MyAdapter.Holder>(), Filterable {

    var itemFilterList = ArrayList<MyModel>()

    init {
        itemFilterList = itemList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val myModelList =  LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        val sch = Holder(myModelList)
        return sch
    }

    override fun getItemCount(): Int{
        return itemFilterList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = itemFilterList[position]
        holder.id.text = model.id.toString()
        holder.name.text = model.name
        holder.phone.text = model.phone.toString()
    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = itemView.myId
        val name = itemView.name
        val phone = itemView.phone
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemFilterList = itemList
                } else {
                    val resultList = ArrayList<MyModel>()
                    for (row in itemList){

                        if(row.name.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))){
                            resultList.add(row)
                        }
                    }
                    itemFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, result: FilterResults?){
                itemFilterList = result?.values as ArrayList<MyModel>
                notifyDataSetChanged()
            }

        }
    }
}