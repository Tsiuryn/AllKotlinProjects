package alex.ts.myprojects.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences {
    companion object{
        private const val PREFERENCES = "PREFERENCES"
        private const val CURRENT_PATH = "CURRENT_PATH"

        fun getCurrentPath (context: Context): String?{
           val pref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
            return pref.getString(CURRENT_PATH, "master")
        }

        fun setCurrentPath (context: Context, path: String){
            val pref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
            pref.edit().putString(CURRENT_PATH, path).apply()
        }

    }
}