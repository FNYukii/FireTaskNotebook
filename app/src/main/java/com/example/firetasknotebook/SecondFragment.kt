package com.example.firetasknotebook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
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

        val db = FirebaseFirestore.getInstance()
        db.collection("todos")
            .whereEqualTo("isAchieved", true)
            .get()
            .addOnSuccessListener { documents ->

                val todos = ArrayList<Todo>()
                documents.forEach {
                    todos.add(it.toObject(Todo::class.java))
                }

                if (todos.size == 0) {
                    noAchievedTodoText.visibility = View.VISIBLE
                } else {
                    noAchievedTodoText.visibility = View.INVISIBLE
                }

                recyclerView02.adapter = TodoRecyclerViewAdapter(todos)
            }
            .addOnFailureListener {
                Log.d("hello", "失敗")
            }
    }
}