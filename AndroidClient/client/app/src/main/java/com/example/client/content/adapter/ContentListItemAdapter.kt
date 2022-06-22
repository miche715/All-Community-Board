package com.example.client.content.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.content.domain.Content

class ContentListItemAdapter(private val context: Context) : RecyclerView.Adapter<ContentListItemAdapter.ViewHolder>()
{
    var contents = mutableListOf<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(context).inflate(R.layout.content_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val content = contents[position]
        val listener = View.OnClickListener()
        {
            Toast.makeText(it.context, "Clicked -> contentId: ${content.contentId}, title: ${content.title}, commentNum: ${content.commentNum}", Toast.LENGTH_SHORT).show()
        }

        holder.bind(listener, content)

        Log.d("게시글 수신", content.toString())
    }

    override fun getItemCount(): Int = contents.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private val titleTextview = itemView.findViewById<TextView>(R.id.titleTextview)
        private val createdAtTextview = itemView.findViewById<TextView>(R.id.createdAtTextview)
        private val writerTextview = itemView.findViewById<TextView>(R.id.writerTextview)
        private val goodTextview = itemView.findViewById<TextView>(R.id.goodTextview)
        private val badTextview = itemView.findViewById<TextView>(R.id.badTextview)
        private val commentNumTextview = itemView.findViewById<TextView>(R.id.commentNumTextview)

        fun bind(listener: View.OnClickListener, item: Content)
        {
            titleTextview.text = item.title
            createdAtTextview.text = item.createdAt
            writerTextview.text = item.writer
            goodTextview.text = item.good.toString()
            badTextview.text = item.bad.toString()
            commentNumTextview.text = item.commentNum.toString()
            itemView.setOnClickListener(listener)
        }
    }
}
