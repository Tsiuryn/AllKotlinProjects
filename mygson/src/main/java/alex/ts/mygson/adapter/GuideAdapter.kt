package alex.ts.mygson.adapter

import alex.ts.mygson.R
import alex.ts.mygson.model.MyContactModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class GuideAdapter(private var list: Array<File>, private val listener: GuideAdapterListener) :
    RecyclerView.Adapter<GuideAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_guide, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val file = list[position]
        holder.name.text = file.name
        val sizeOfFile = file.length() / 1024
        holder.size.text = "$sizeOfFile  kb"
        if(file.isFile){
            holder.image.setImageResource(R.drawable.ic_file)
        }else holder.image.setImageResource(R.drawable.ic_folder)
        holder.itemView.setOnClickListener{
            listener.itemClick(file)
        }
    }

    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.itemGuideNameFolder)
        val size = view.findViewById<TextView>(R.id.itemGuideSize)
        val image = view.findViewById<ImageView>(R.id.itemGuideImageView)
    }

    fun updateAdapter(newList: Array<File>){
        list = newList
        notifyDataSetChanged()
    }

    interface GuideAdapterListener{
        fun itemClick(file: File)
    }
}