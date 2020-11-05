package alex.ts.myprojects

import alex.ts.myprojects.database.Note
import alex.ts.myprojects.database.NotesDatabase
import alex.ts.myprojects.utils.Constants.Companion.DESCRIPTION
import alex.ts.myprojects.utils.Constants.Companion.ENF_DESCRIPTION
import alex.ts.myprojects.utils.Constants.Companion.ENF_ID
import alex.ts.myprojects.utils.Constants.Companion.ENF_TITLE
import alex.ts.myprojects.utils.Constants.Companion.ID
import alex.ts.myprojects.utils.Constants.Companion.TITLE
import alex.ts.myprojects.utils.Preferences
import alex.ts.myprojects.utils.backToHeadFolder
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit_note.*
import kotlinx.coroutines.launch

class EditNoteFragment: BaseFragment(){
     private var myId = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (bundle != null){
            myId = bundle.getInt(ID)
            getNoteById(myId)
        }
        checkNotes.setOnClickListener {
            sendDataToNoteFragment()
        }
    }

    private fun getNoteById (id: Int){
        launch {
            context!!.let {
                val note = NotesDatabase.getNotesDataBase(it)!!.getNotesDAO().getNote(id)
                edit_title.setText(note.title)
                edit_description.setText(note.description)
            }
        }
    }

    private fun sendDataToNoteFragment() {
        val noteFragment = NoteFragment()
        val path = Preferences.getCurrentPath(requireContext())?.let { backToHeadFolder(it) }
        if (path != null) {
            Preferences.setCurrentPath(requireContext(), path)
        }
        saveNoteToDB()
    }

    private fun saveNoteToDB (){
        launch {
            context!!.let {
                val note = NotesDatabase.getNotesDataBase(it)!!.getNotesDAO().getNote(myId)
                note.description = edit_description.text.toString()
                note.title = edit_title.text.toString()
                NotesDatabase.getNotesDataBase(it)!!.getNotesDAO().updateNote(note)
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_container, NoteFragment()).commit()
                clearFragmentBackStack()
            }
        }
    }

    //очистка стэка
    private fun clearFragmentBackStack (){
        val fm = activity?.supportFragmentManager
        for (i in 0 until fm!!.backStackEntryCount){
            fm.popBackStack()
        }
    }
}