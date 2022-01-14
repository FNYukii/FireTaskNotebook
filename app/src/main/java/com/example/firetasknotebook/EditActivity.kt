package com.example.firetasknotebook

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

class EditActivity : AppCompatActivity() {

    private var id: String? = null
    private var createdAt = Date()
    private var isAchieved = false
    private var achievedAt: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //既存Todo編集時は、編集対象ドキュメントのidを取得
        id = intent.getStringExtra("id")

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
                    createdAt = document.getTimestamp("created_at")!!.toDate()
                    isAchieved = document.getBoolean("isAchieved")!!
                    val timestamp = document.getTimestamp("achieved_at")
                    if (timestamp != null) {
                        achievedAt = timestamp.toDate()
                    }
                    //達成済みなら達成日時を表示する
                    if (isAchieved) {
                        achievedAtContainer.visibility = View.VISIBLE
                        setAchievedDateTimeText()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }

        backButton.setOnClickListener {
            saveTodo()
            finish()
        }

        achieveButton.setOnClickListener {
            isAchieved = !isAchieved
            achievedAt = if (isAchieved) {
                Date()
            } else {
                null
            }
            saveTodo()
            finish()
        }

        deleteButton.setOnClickListener {
            if (id != null) {
                deleteTodo()
            }
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
            "created_at" to com.google.firebase.Timestamp(Date()),
            "isAchieved" to isAchieved,
            "achieved_at" to achievedAt?.let { com.google.firebase.Timestamp(it) }
        )

        db.collection("todos")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "ドキュメント追加成功! ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "ドキュメント追加失敗", e)
            }
    }

    private fun updateTodo() {
        val db = FirebaseFirestore.getInstance()

        val todo = hashMapOf(
            "content" to contentEdit.text.toString(),
            "isAchieved" to isAchieved,
            "created_at" to com.google.firebase.Timestamp(createdAt),
            "achieved_at" to achievedAt?.let { com.google.firebase.Timestamp(it) }
        )

        db.collection("todos").document(id!!)
            .set(todo)
            .addOnSuccessListener { Log.d(TAG, "ドキュメント更新成功!") }
            .addOnFailureListener { e -> Log.w(TAG, "ドキュメント更新失敗!", e) }
    }

    private fun deleteTodo() {
        val db = FirebaseFirestore.getInstance()

        db.collection("todos").document(id!!)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "ドキュメント削除成功!") }
            .addOnFailureListener { e -> Log.w(TAG, "ドキュメント削除失敗!", e) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setAchievedDateTimeText(){
        //達成年日の文字列を生成
        val dateFormatter = SimpleDateFormat("yyyy年 M月 d日")
        val achievedDateString: String = dateFormatter.format(achievedAt!!)

        //達成曜日の文字列を生成
        val achievedLocalDate = achievedAt!!.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val achievedDayOfWeek: Int =  achievedLocalDate.dayOfWeek.value
        var achievedDayOfWeekString = ""
        when(achievedDayOfWeek){
            1 -> achievedDayOfWeekString = " (日)"
            2 -> achievedDayOfWeekString = " (月)"
            3 -> achievedDayOfWeekString = " (火)"
            4 -> achievedDayOfWeekString = " (水)"
            5 -> achievedDayOfWeekString = " (木)"
            6 -> achievedDayOfWeekString = " (金)"
            7 -> achievedDayOfWeekString = " (土)"
        }

        //達成日をTextViewへセット
        achievedDateText.text = (achievedDateString + achievedDayOfWeekString)

        //達成時刻をTextViewへセット
        val timeFormatter = SimpleDateFormat("HH:mm")
        achievedTimeText.text = timeFormatter.format(achievedAt!!)
    }
}