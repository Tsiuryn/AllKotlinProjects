package alex.ts.mygson.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alex.ts.mygson.R
import alex.ts.mygson.adapter.GuideAdapter
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*


class GuideFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var myAdapter: GuideAdapter
    private var rootPath = ""
    private lateinit var myDir: File

    companion object{
        const val SEND_STRING_TO_LISTFRAGMENT = "SEND_STRING_TO_LISTFRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.guideRecycler)
        getListFiles()
        val backBtn = view.findViewById<FloatingActionButton>(R.id.guideBtnUp)
        backBtn.setOnClickListener{
            goToParentFolder(myDir)
        }
    }

    private fun getListFiles(){
        rootPath = Environment.getExternalStorageDirectory().toString()
        myDir = File(rootPath)
        val files = myDir.listFiles()
        if(files != null){
            createAdapter(files)
            for (element in files){
                Log.d("TAG", "getListFiles: ${element.absolutePath}")
            }
        }
    }

    private fun createAdapter(list: Array<File>) {
        recycler.layoutManager = LinearLayoutManager(context)
        myAdapter = GuideAdapter(list, object : GuideAdapter.GuideAdapterListener{
            override fun itemClick(file: File) {
                if (!file.isFile){
                    openFolder(file)
                    myDir = file
                    Toast.makeText(requireContext(), "You click on ${file.name} + ${file.absolutePath}", Toast.LENGTH_SHORT).show()
                }
                else{
                    importFileToList(file)
                }
            }
        })
        recycler.adapter = myAdapter
    }

    private fun importFileToList(file: File) {
       val extension = file.extension
        Log.d("TAG", "importFileToList: $extension")
        if(extension == "tsa"){
            Toast.makeText(requireContext(), "TSA", Toast.LENGTH_SHORT).show()
            val json = readFromFile(file)
            val bundle = Bundle()
            bundle.putString(SEND_STRING_TO_LISTFRAGMENT, json)
            val listFragment = ListContactsFragment()
            listFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.myContainer, listFragment).commit()
        }
    }

    private fun readFromFile(file: File): String{
        var contacts = ""
        try {
            file.bufferedReader().forEachLine {
                contacts = contacts + it + "\n"
            }
            Log.d("TAG", "writeFile: ${contacts}")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return contacts
    }

    private fun openFolder (file: File){
        rootPath = file.absolutePath
        val dir= File(rootPath)
        val files = dir.listFiles()
        if (files != null){
            myAdapter.updateAdapter(files)
        }
    }

    private fun getParentPath(path: String): String{
        var index = path.length
        if (path.contains('/')){
            index = path.lastIndexOf('/')
        }
        return path.substring(0, index)
    }

    private fun goToParentFolder(file: File){
        val parentPath = getParentPath(file.absolutePath)
        val dir= File(parentPath)
        val files = dir.listFiles()
        if (files != null){
            myDir = dir
            myAdapter.updateAdapter(files)
        }
    }
}