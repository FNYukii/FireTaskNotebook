package com.example.firetasknotebook

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: Firestoreのtodosコレクションを取得する

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
            Log.d(TAG, "todo contents: $contents")
        }



        //TODO: RecyclerViewを表示する
    }

}