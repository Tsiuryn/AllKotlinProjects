package my.app.ts_pomodoro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ts.alex.dialogcustom.R
import java.util.*
import kotlin.collections.ArrayList


class DialogAdapter(private var itemList: List<String>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<DialogAdapter.Holder>(), Filterable {
    var itemFilterList = ArrayList<String>()

    init {
        itemFilterList = itemList as ArrayList<String>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val myJobList= LayoutInflater.from(parent.context).inflate(R.layout.item_dialog, parent, false)
        val sch = Holder(myJobList)
        return sch
    }

    override fun getItemCount(): Int = itemFilterList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val myJob = itemFilterList[position]
        holder.text.text = myJob

        holder.itemView.setOnClickListener{
            onClickListener.onItemClick(myJob, position + 1)
        }

    }



    class Holder (itemView: View): RecyclerView.ViewHolder(itemView){
        val text = itemView.findViewById<TextView>(R.id.vText)

    }
    interface OnClickListener {
        fun onItemClick(text: String, idOfItem: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemFilterList = itemList as ArrayList<String>
                } else {
                    val resultList = ArrayList<String>()
                    for (row in itemList){

                        if(row.toLowerCase(Locale.ROOT)
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
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

}
