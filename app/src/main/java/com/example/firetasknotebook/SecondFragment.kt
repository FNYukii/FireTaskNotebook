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

        recyclerView02.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onStart() {
        super.onStart()

        //RecyclerView with Cloud Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("todos")
            .whereEqualTo("isAchieved", true)
            .orderBy("created_at", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Snapshotリッスン失敗!", e)
                    return@addSnapshotListener
                }

                val todos = ArrayList<Todo>()
                value!!.forEach {
                    todos.add(it.toObject(Todo::class.java))
                }

                recyclerView02.adapter = TodoRecyclerViewAdapter(todos)
            }
    }
}