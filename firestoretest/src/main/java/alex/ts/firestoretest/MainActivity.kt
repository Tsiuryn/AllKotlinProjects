package alex.ts.firestoretest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firestore = Firebase.firestore

        sendInfo.setOnClickListener{
            val comment = editText.text?.toString()
            if (comment.isNullOrBlank()){
                return@setOnClickListener
            }
            firestore.collection("feedback")
                .add(hashMapOf("comment" to comment))
//                .add(comment)
                .addOnSuccessListener {
                    val text = "Success!"
                    val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
                }

            Log.d("TAG", "send info")
        }
    }
}
