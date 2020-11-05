package alex.ts.myprojects.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getNotesDAO(): NotesDAO

    companion object{
        var INSTANCE: NotesDatabase? = null

        fun getNotesDataBase (context: Context): NotesDatabase?{
            if (INSTANCE == null){
                synchronized(NotesDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "my_notes").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE == null
        }
    }
}