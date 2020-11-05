package alex.ts.mygson.adapter

import alex.ts.mygson.R
import alex.ts.mygson.model.MyContactModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val list: List<MyContactModel>) :
    RecyclerView.Adapter<ListAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val contact = list[position]
        holder.myId.text = contact.id.toString()
        holder.name.text = contact.name
        holder.date.text = contact.date
    }

    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val myId = view.findViewById<TextView>(R.id.itemId)
        val name = view.findViewById<TextView>(R.id.itemName)
        val date = view.findViewById<TextView>(R.id.itemDate)

    }
}