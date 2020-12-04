package by.st.generatorforms.recyclerpayload.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.st.generatorforms.recyclerpayload.R
import by.st.generatorforms.recyclerpayload.entity.MyEntity

class MyAdapter(private var list: MutableList<MyEntity>) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.my_item, parent, false)
    )

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val myEntity = list[position]
        holder.holderId.text = myEntity.id.toString()
        holder.holderName.text = myEntity.name
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int, payloads: MutableList<Any>) {
        val myEntity = list[position]

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle = payloads[0] as Bundle
            bundle.keySet().forEach {
                if (it == "Name") {
                    val name = bundle.getString("Name")!!
                    Log.d("TAG", "onBindViewHolder: $position, $name")
                    myEntity.name = name
                    super.onBindViewHolder(holder, position, payloads)
                }
            }
        }
    }

    override fun getItemCount() = list.size

    fun updateAdapter(myListEntity: ArrayList<MyEntity>) {
        val newList = myListEntity.map{it.copy()}
        val diffResult = DiffUtil.calculateDiff(ExampleDiffUtil(list, newList))
        list = newList as MutableList<MyEntity>
        diffResult.dispatchUpdatesTo(this)
    }

    class MyHolder(item: View) : RecyclerView.ViewHolder(item) {
        val holderId = item.findViewById<TextView>(R.id.myId)
        val holderName = item.findViewById<TextView>(R.id.myName)

    }

}