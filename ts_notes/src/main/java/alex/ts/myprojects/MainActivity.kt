package alex.ts.myprojects

import alex.ts.myprojects.database.Note
import alex.ts.myprojects.utils.Preferences
import alex.ts.myprojects.utils.backToHeadFolder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, NoteFragment())
            .commit()
    }

    override fun onBackPressed() {
        val currentPath = Preferences.getCurrentPath(this)
        val path = Preferences.getCurrentPath(this)?.let { backToHeadFolder(it) }
        if (!path.equals("master")) {
            if (path != null) {
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show()
                Preferences.setCurrentPath(this, path)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, NoteFragment()).commit()
            }
        } else if (path.equals("master")) {
            if (path.equals(currentPath)) {
                dialogCloseApp()
                return
            }
            if (path != null) {
                Preferences.setCurrentPath(this, path)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, NoteFragment()).commit()
            }

        }
    }

    private fun dialogCloseApp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Question:")
            .setMessage(getString(R.string.close_app))
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, which ->

            }

            .setPositiveButton("Yes") { dialog, which ->
                super.onBackPressed()
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}
