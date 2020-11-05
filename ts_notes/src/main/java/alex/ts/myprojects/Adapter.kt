package alex.ts.myprojects

import alex.ts.myprojects.database.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(val list: ArrayList<Note>, private val clickListener: OnClickListener) : RecyclerView.Adapter<Adapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
            )
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val note = list[position]
            holder.title.text = note.title
            holder.description.text = note.description
            if (note.isFolder){
                holder.image.setImageResource(R.drawable.ic_folder)
            }else holder.image.setImageResource(R.drawable.ic_file)

            holder.itemView.setOnClickListener { clickListener.onItemClick(note) }

        }

        fun updateAdapter(newList: List<Note>){
            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }

        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title = itemView.findViewById<TextView>(R.id.item_title)
            val description = itemView.findViewById<TextView>(R.id.item_description)
            val image = itemView.findViewById<ImageView>(R.id.item_folder)
        }
    interface OnClickListener {
        fun onItemClick(note: Note?)
    }
}

