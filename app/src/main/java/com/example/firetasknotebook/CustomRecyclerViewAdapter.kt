package com.example.firetasknotebook

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolderList (item: View) : RecyclerView.ViewHolder(item) {
    val contentText: TextView = item.findViewById(R.id.contentText)
}

class CustomRecyclerAdapter(private val todos: ArrayList<Todo>) : RecyclerView.Adapter<ViewHolderList>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderList {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.one_todo, parent, false)
        return ViewHolderList(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
        holder.contentText.text = todos[position].content
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("id", todos[position].id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = todos.size
}