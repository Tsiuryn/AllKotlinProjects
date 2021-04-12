package com.ts.alex.dialogcustom

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ts.alex.dialogcustom.databinding.DialogCustomFragmentBinding
import my.app.ts_pomodoro.adapter.DialogAdapter

class CustomDialogFragment(val onItemClick: onClickItem): DialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DialogAdapter
    private lateinit var binding: DialogCustomFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCustomFragmentBinding.inflate(inflater,container,false)
        if (dialog != null && dialog?.window != null) {
            requireActivity().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }

            return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.vRecycler

        val b = arguments
        val list = b?.getStringArrayList(MainActivity.FROM_ACTIVITY_TO_FRAGMENT)
        createAdapter(list as List<String>)
        dialog?.setTitle("Выберите значение:")

        dialog?.window!!.setBackgroundDrawableResource(R.drawable.background)

        binding.vSearch.doAfterTextChanged {
            adapter.filter.filter(it.toString())
        }

    }

    private fun createAdapter(list: List<String>) {

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = DialogAdapter(list, object : DialogAdapter.OnClickListener{
            override fun onItemClick(text: String, idOfItem: Int) {
                onItemClick.onClick(text)
                dismiss()
            }
        })
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }


}
interface onClickItem{
    fun onClick(text: String)
}