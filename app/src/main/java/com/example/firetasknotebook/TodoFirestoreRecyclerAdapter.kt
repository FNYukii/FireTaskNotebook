package com.example.firetasknotebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.one_todo.view.*


class TodoViewHolder internal constructor(private val itemView: View) : RecyclerView.ViewHolder(itemView){
    internal fun setContent(content: String){
        itemView.contentText.text = content
    }
}

class TodoFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<Todo>):
    FirestoreRecyclerAdapter<Todo, TodoViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_todo,parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int, model: Todo) {
        holder.setContent(model.content)
    }
}