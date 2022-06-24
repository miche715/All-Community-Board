package com.example.client.content.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.content.domain.Content

class ContentListItemAdapter(private val context: Context) : RecyclerView.Adapter<ContentListItemAdapter.ViewHolder>()
{
    var contents = mutableListOf<Content>()

    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener
    {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener)
    {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  // 아이템 레이아웃과 결합
    {
        val view = LayoutInflater.from(context).inflate(R.layout.content_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  // View에 내용 입력
    {
        val content = contents[position]

        holder.itemView.setOnClickListener()
        {
            itemClickListener.onClick(it, position)
        }

        holder.bind(content)

        Log.d("게시글 수신", content.toString())
    }

    override fun getItemCount(): Int = contents.size  // 리스트 내 아이템 개수

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  // 레이아웃 내 View 연결
    {
        private val titleTextview = itemView.findViewById<TextView>(R.id.titleTextview)
        private val createdAtTextview = itemView.findViewById<TextView>(R.id.createdAtTextview)
        private val writerTextview = itemView.findViewById<TextView>(R.id.writerTextview)
        private val goodTextview = itemView.findViewById<TextView>(R.id.goodTextview)
        private val commentNumTextview = itemView.findViewById<TextView>(R.id.commentNumTextview)

        fun bind(item: Content)
        {
            titleTextview.text = item.title
            createdAtTextview.text = item.createdAt
            writerTextview.text = item.writer
            goodTextview.text = item.goodNum.toString()
            commentNumTextview.text = item.commentNum.toString()
        }
    }
}