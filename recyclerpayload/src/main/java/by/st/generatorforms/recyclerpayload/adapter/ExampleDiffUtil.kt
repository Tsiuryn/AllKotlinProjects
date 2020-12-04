package by.st.generatorforms.recyclerpayload.adapter

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import by.st.generatorforms.recyclerpayload.entity.MyEntity

class ExampleDiffUtil(
    private val oldList: List<MyEntity>,
    private val newList: List<MyEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMyEntity = oldList[oldItemPosition]
        val newMyEntity = newList[newItemPosition]
        Log.d("TAG", "areItemsTheSame: ${oldMyEntity.id == newMyEntity.id}")
        return oldMyEntity.id == newMyEntity.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMyEntity = oldList[oldItemPosition]
        val newMyEntity = newList[newItemPosition]
        Log.d("TAG", "areContentsTheSame: ${oldMyEntity == newMyEntity}")
        return oldMyEntity == newMyEntity
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newEntity = newList[newItemPosition]
        val oldEntity = oldList[oldItemPosition]
        val bundle = Bundle()
        if (newEntity.name != oldEntity.name && newEntity.id == oldEntity.id){
            bundle.putString("Name", newEntity.name)
        }
        if (bundle.size() == 0){
            return null
        }
        return bundle
    }
}