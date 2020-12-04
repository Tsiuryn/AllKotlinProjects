package alex.ts.mygson.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GetPermissions(private val context: Context, private val myActivity: AppCompatActivity) {

    companion object{
       const val STORAGE_PERMISSION_CODE = 2
    }

    fun checkPermission (){
        Log.d ("TAG", "button")
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                context,
                "You have already granted this permission!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
             requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        var getPerm = false
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                myActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Log.d ("TAG", "dialog")
            AlertDialog.Builder(context)
                .setTitle("Permission needed")
                .setPositiveButton("ok"
                ) { dialog, which ->
                    ActivityCompat.requestPermissions(
                        myActivity,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        STORAGE_PERMISSION_CODE
                    )
                }
                .setNegativeButton("cancel"
                ) { dialog, which -> dialog!!.dismiss() }.create().show()
        }else {
            ActivityCompat.requestPermissions(myActivity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE);
        }
    }


}