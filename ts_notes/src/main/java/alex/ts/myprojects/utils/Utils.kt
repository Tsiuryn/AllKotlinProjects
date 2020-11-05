package alex.ts.myprojects.utils

import alex.ts.myprojects.database.Note
import android.util.Log

fun getFoldersByPath (list: List<Note>, path: String?): List<Note>{
    val sortedList = ArrayList<Note>()
    for (i in 0 until list.size){
        if (list[i].path == path){
            sortedList.add(list[i])
        }
    }
    return sortedList.sortedBy { it.description }
}

fun listDeletedAttachments (fullListDB: List<Note>, path: String): List<Note>{
    var myList = ArrayList<Note>()
    for (i in 0 until fullListDB.size){
        if (fullListDB[i].path!!.startsWith(path)){
            myList.add(fullListDB[i])
        }
    }
    return myList
}

fun createLog (message: String){
    Log.d ("TAG", message)
}

fun backToHeadFolder (path: String): String{
    var index = path.length
    if (path.contains('/')){
        index = path.lastIndexOf('/')
    }
    return path.substring(0, index)
}