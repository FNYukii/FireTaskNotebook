package com.example.firetasknotebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {

    private var id = 0
    private var isAchieved = false
    private var achievedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //Intentにidがあれば取得
        id = intent.getIntExtra("id", 0)

        //既存Todo更新時
        if(id != 0){
            //TODO: 編集対象のドキュメントを取得する
            //TODO: 各変数へ値を格納する
        }

        backButton.setOnClickListener {
            saveTodo()
            finish()
        }

        achieveButton.setOnClickListener {
            isAchieved = !isAchieved
            if (isAchieved) {
                achievedDate = Date()
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
        if (id == 0 && contentEdit.text.isNotEmpty()){
            insertTodo()
        } else if (id != 0 && contentEdit.text.isNotEmpty()) {
            updateTodo()
        }
    }

    private fun insertTodo() {
        //TODO: Firestoreに新規ドキュメントを追加する
    }

    private fun updateTodo() {
        //TODO: Firestore上のドキュメントを更新する
    }

    private fun deleteTodo() {
        //TODO: Firestore上のドキュメントを削除する
    }

}