package alex.ts.myprojects.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "my_notes")
data class Note(
    val isFolder: Boolean = true,
    var title: String = "",
    var description: String = "",
    val uuid: String = "",
    val path: String = ""
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


}