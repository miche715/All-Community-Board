package com.example.client.comment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.comment.domain.Comment

class CommentListItemAdapter(private val context: Context) : RecyclerView.Adapter<CommentListItemAdapter.ViewHolder>()
{
    var comments = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListItemAdapter.ViewHolder  // 아이템 레이아웃과 결합
    {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentListItemAdapter.ViewHolder, position: Int)  // View에 내용 입력
    {
        val comment = comments[position]

        holder.bind(comment)

        Log.d("댓글 수신", comment.toString())
    }

    override fun getItemCount(): Int = comments.size  // 리스트 내 아이템 개수

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  // 레이아웃 내 View 연결
    {
        private val textTextview = itemView.findViewById<TextView>(R.id.textTextview)
        private val createdAtTextview = itemView.findViewById<TextView>(R.id.createdAtTextview)
        private val writerTextview = itemView.findViewById<TextView>(R.id.writerTextview)

        fun bind(item: Comment)
        {
            textTextview.text = item.text
            createdAtTextview.text = item.createdAt
            writerTextview.text = item.writer
        }
    }
}