package alex.ts.myprojects

import alex.ts.myprojects.Adapter.OnClickListener
import alex.ts.myprojects.database.Note
import alex.ts.myprojects.database.NotesDatabase
import alex.ts.myprojects.utils.Constants.Companion.DESCRIPTION
import alex.ts.myprojects.utils.Constants.Companion.ENF_DESCRIPTION
import alex.ts.myprojects.utils.Constants.Companion.ENF_ID
import alex.ts.myprojects.utils.Constants.Companion.ENF_TITLE
import alex.ts.myprojects.utils.Constants.Companion.ID
import alex.ts.myprojects.utils.Constants.Companion.TITLE
import alex.ts.myprojects.utils.Preferences
import alex.ts.myprojects.utils.createLog
import alex.ts.myprojects.utils.getFoldersByPath
import alex.ts.myprojects.utils.listDeletedAttachments
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.ClassCastException
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class NoteFragment : BaseFragment() {
    private var list = ArrayList<Note>()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: Adapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recycler)
        createAdapter()
        updateAdapter()
        addNotes.setOnClickListener(View.OnClickListener {
            dialogCreateFolder()
        })
        itemSwipe(recycler)
//        val bundle = arguments
//        if(bundle != null){
//            createLog("Bundle is not null")
//            getDataFromEditNoteFragment(bundle)
//        }

    }
//
//    private fun getDataFromEditNoteFragment(bundle: Bundle) {
//        val id = bundle.getInt(ENF_ID)
//        launch {
//            context?.let {
//                list = NotesDatabase(it).getNotesDAO().getAllNotes() as ArrayList<Note>
//                var note =  Note()
//                for (i in 0 until list.size){
//                    if (list[i].id == id){
//                        note = list[i]
//                    }
//
//                }
//                note.title = bundle.getString(ENF_TITLE).toString()
//                note.description = bundle.getString(ENF_DESCRIPTION).toString()
//                updateNoteInDB(note)
//            }
//        }
//    }

//    private fun updateNoteInDB(note: Note) {
//        launch {
//            context?.let {
//                NotesDatabase(it).getNotesDAO().updateNote(note)
//                updateAdapter()
//                Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


    private fun addNoteToDB(note: Note) {
        launch {
            context?.let {
                NotesDatabase(it).getNotesDAO().addNote(note)
                updateAdapter()
                Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateAdapter() {
        launch {
            context?.let {
                list = NotesDatabase(it).getNotesDAO().getAllNotes() as ArrayList<Note>
                Log.d("TAG", "В базе ${list.size} записей")
                val sortedList =
                    getFoldersByPath(list, Preferences.getCurrentPath(requireContext()))
                adapter.updateAdapter(sortedList)

            }
        }
    }

    private fun createAdapter() {
        recycler.layoutManager = LinearLayoutManager(context)
        adapter = Adapter(list, object : OnClickListener {
            override fun onItemClick(note: Note?) {
                if (note != null) {
                    if (note.isFolder) {
                        setPathToPreferenses(note)
                        updateAdapter()
                    }else{
                        Preferences.setCurrentPath(requireContext(), "${Preferences.getCurrentPath(requireContext())}/")
                        val editNote = EditNoteFragment()
                        val bundle = Bundle()
                        bundle.putInt(ID, note.id)
//                        bundle.putString(TITLE, note.title)
//                        bundle.putString(DESCRIPTION, note.description)
                        editNote.arguments = bundle
                        activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_container, editNote).addToBackStack("").commit()
                    }
                }
            }

        })
        recycler.adapter = adapter
    }

    private fun setPathToPreferenses(note: Note){
        val path =
            getPathFromFolder(note)
        Preferences.setCurrentPath(requireContext(), path)
        Log.d("TAG", path)
    }
    private fun getPathFromFolder(note: Note): String = Preferences.getCurrentPath(requireContext()) + "/${note.uuid}-${note.id}"

    private fun dialogCreateFolder() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Question:")
            .setMessage("Do you want to create file or folder?")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, which ->
                cancel()
            }
            .setNeutralButton("Folder") { dialog, which ->
                createFile(true, "")
            }
            .setPositiveButton("File") { dialog, which ->
                createFile(false, "New description")
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun createFile(isFolder: Boolean, description: String) {
        val path: String? = Preferences.getCurrentPath(requireContext())
        val time = System.currentTimeMillis()
        val uuid = UUID.randomUUID().toString()
        val note = path?.let { Note(isFolder, "New title - $time", description, uuid , it) }
        if (note != null) {
            addNoteToDB(note)
        }
        Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()
    }

    //Свайп для items адаптера
    private fun itemSwipe(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.list[viewHolder.adapterPosition]
                Log.d("TAG", "${note}")
                dialogDeleteItem(note)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }


        }).attachToRecyclerView(recyclerView)
    }

    //диалог- для удаления item
    private fun dialogDeleteItem(note: Note) {
        val path = getPathFromFolder(note)
        val list = listDeletedAttachments(this.list, path)
        AlertDialog.Builder(requireContext())
            .setMessage("Do you want to delete item - ${note.title}")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, which ->
                if (note.isFolder){
                    if (list.isNotEmpty()){
                        dialogDeleteFolder(note, list)
                    }else deleteItemFromDatabase(note)

                }else{
                    deleteItemFromDatabase(note)
                }
                updateAdapter()
                dialog.dismiss()
            }
            .setNegativeButton("CANCEL") { dialog, which ->
                updateAdapter()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun dialogDeleteFolder(note: Note, listAttachment: List<Note>) {
            AlertDialog.Builder(requireContext())
                .setMessage("Do you want to delete all attachments (${list.size}")
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, which ->
                    deleteItemFromDatabase(note)
                    for (i in 0 until listAttachment.size){
                        deleteItemFromDatabase(listAttachment[i])
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("CANCEL") { dialog, which ->
                    updateAdapter()
                    dialog.dismiss()
                }
                .create()
                .show()


    }

    private fun deleteItemFromDatabase(note: Note) {
        launch {
            context?.let {
                NotesDatabase(it).getNotesDAO().deleteNote(note)
                updateAdapter()
            }
        }
    }
}