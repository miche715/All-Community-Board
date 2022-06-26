package com.example.client.comment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.comment.domain.Comment
import com.example.client.user.domain.User

class CommentListItemAdapter(private val context: Context, private val user: User) : RecyclerView.Adapter<CommentListItemAdapter.ViewHolder>()
{
    var comments = mutableListOf<Comment>()

    private lateinit var itemClickListener : CommentListItemAdapter.OnItemClickListener

    interface OnItemClickListener
    {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: CommentListItemAdapter.OnItemClickListener)
    {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  // 아이템 레이아웃과 결합
    {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  // View에 내용 입력
    {
        val comment = comments[position]

        holder.bind(comment)
    }

    override fun getItemCount(): Int = comments.size  // 리스트 내 아이템 개수

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  // 레이아웃 내 View 연결
    {
        private val textTextview = itemView.findViewById<TextView>(R.id.textTextview)
        private val createdAtTextview = itemView.findViewById<TextView>(R.id.createdAtTextview)
        private val writerTextview = itemView.findViewById<TextView>(R.id.writerTextview)
        private val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)

        fun bind(item: Comment)
        {
            textTextview.text = item.text
            createdAtTextview.text = item.createdAt
            writerTextview.text = item.writer
            if(item.writer != user.username)
            {
                deleteButton.visibility = View.INVISIBLE
            }
            else
            {
                deleteButton.setOnClickListener()
                {
                    itemClickListener.onClick(deleteButton, adapterPosition)
                }
            }
        }
    }
}