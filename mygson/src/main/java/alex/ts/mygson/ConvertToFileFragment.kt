package alex.ts.mygson

import alex.ts.mygson.ListContactsFragment.Companion.PARCELABLE_LIST_CONTACT
import alex.ts.mygson.model.MyContactModel
import alex.ts.mygson.model.MyListContacts
import alex.ts.mygson.permission.GetPermissions
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import java.io.*


class ConvertToFileFragment : Fragment() {
    private lateinit var permissions: GetPermissions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        permissions = GetPermissions(requireContext(), activity as AppCompatActivity)
        return inflater.inflate(R.layout.fragment_convert_to_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = getArgumentsFromListFragment()
        val text = createJson(list)
        val exportButton = view.findViewById<Button>(R.id.btnConvertToFile)
        permissions.checkPermission()
        exportButton.setOnClickListener { writeFile(text) }
    }

    private fun getArgumentsFromListFragment(): List<MyContactModel> {
        return arguments!!.getParcelable<MyListContacts>(PARCELABLE_LIST_CONTACT)!!.listContacts
    }

    private fun createJson(listContacts: List<MyContactModel>): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(listContacts)
    }

    private fun writeFile(text: String) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return
        }
        var sdPath = Environment.getStorageDirectory()
        sdPath = File(sdPath.absolutePath + "/" + "MyApp")
        sdPath.mkdir()
        val sdFile = File(sdPath, "MyContact.ts")
        try {
            val bw = BufferedWriter(FileWriter(sdFile))
            bw.write(text)
            bw.close()
            Log.d("TAG", "writeFile: ${sdFile.absolutePath}")
            Toast.makeText(requireContext(), "File added to ${sdFile.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == GetPermissions.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission GRANTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}