package com.example.firetasknotebook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //FAB
        fab.setOnClickListener {
            val intent = Intent(this.context, EditActivity::class.java)
            startActivity(intent)
        }

        //Set recyclerView layout manager
        recyclerView01.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onStart() {
        super.onStart()

        //Get documents from Cloud Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("todos")
            .whereEqualTo("isAchieved", false)
            .get()
            .addOnSuccessListener { documents ->

                //Convert documents to todos
                val todos = ArrayList<Todo>()
                documents.forEach {
                    todos.add(it.toObject(Todo::class.java))
                }

                //Set message text visibility
                if (todos.size == 0) {
                    noUnachievedTodoText.visibility = View.VISIBLE
                } else {
                    noUnachievedTodoText.visibility = View.INVISIBLE
                }

                //Set recyclerView adapter with todos
                recyclerView01.adapter = TodoRecyclerViewAdapter(todos)
            }
            .addOnFailureListener {
                Log.d("hello", "失敗")
            }
    }
}