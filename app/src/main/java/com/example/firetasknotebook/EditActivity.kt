package com.example.firetasknotebook

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {

    private var id: String? = null
    private var isAchieved = false
    private var achievedAt: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //既存Todo編集時は、編集対象ドキュメントのidを取得
        id = intent.getStringExtra(id)

        //既存Todo更新時
        if(id != null){
            //編集対象のドキュメントを取得する
            val db = FirebaseFirestore.getInstance()

            val docRef = db.collection("todos").document(id!!)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }

                    //各変数へ値を格納する
                    contentEdit.setText(document.getString("content"))

                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }

        Log.d(TAG, "id: $id")


        backButton.setOnClickListener {
            saveTodo()
            finish()
        }

        achieveButton.setOnClickListener {
            isAchieved = !isAchieved
            if (isAchieved) {
                achievedAt = Date()
            }
            saveTodo()
            finish()
        }

        deleteButton.setOnClickListener {
            deleteTodo()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveTodo()
    }

    private fun saveTodo() {
        if (id == null && contentEdit.text.isNotEmpty()){
            insertTodo()
        } else if (id != null && contentEdit.text.isNotEmpty()) {
            updateTodo()
        }
    }

    private fun insertTodo() {
        val db = FirebaseFirestore.getInstance()

        val data = hashMapOf(
            "content" to contentEdit.text.toString(),
            "isAchieved" to isAchieved,
            "created_at" to com.google.firebase.Timestamp(Date()),
            "achieved_at" to achievedAt?.let { com.google.firebase.Timestamp(it) }
        )

        db.collection("todos")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun updateTodo() {
        //TODO: Firestore上のドキュメントを更新する
    }

    private fun deleteTodo() {
        //TODO: Firestore上のドキュメントを削除する
    }

}