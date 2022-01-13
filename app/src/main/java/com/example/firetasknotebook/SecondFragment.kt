package com.example.firetasknotebook

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todosコレクションのスナップショットを取得して表示
        val db = FirebaseFirestore.getInstance()
        db.collection("todos")
            .whereEqualTo("isAchieved", true)
            .orderBy("created_at", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed!", e)
                return@addSnapshotListener
            }

            val todos = ArrayList<Todo>()
            for (doc in value!!) {
                val id = doc.id
                val content = doc.get("content").toString()
                todos.add(Todo(id, content))
            }

            recyclerView02.adapter = CustomRecyclerAdapter(todos)
            recyclerView02.layoutManager = LinearLayoutManager(this.context)
        }
    }

}