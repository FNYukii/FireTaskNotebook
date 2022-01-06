package com.example.firetasknotebook

import android.content.ContentValues.TAG
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

        val db = FirebaseFirestore.getInstance()
        db.collection("todos").addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed!", e)
                return@addSnapshotListener
            }

            val contents = ArrayList<String>()
            for (doc in value!!) {
                doc.getString("content")?.let {
                    contents.add(it)
                }
            }
            recyclerView01.adapter = CustomRecyclerAdapter(contents)
            recyclerView01.layoutManager = LinearLayoutManager(this.context)
        }
    }
}