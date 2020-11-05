package alex.ts.myprojects.database

import alex.ts.myprojects.database.Note
import androidx.room.*

@Dao
interface NotesDAO {

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM my_notes")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM my_notes WHERE id ==:id")
    suspend fun getNote(id: Int): Note

}