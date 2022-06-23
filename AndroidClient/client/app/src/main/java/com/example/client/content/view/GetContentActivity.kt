package com.example.client.content.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.client.content.domain.Content
import com.example.client.databinding.ActivityGetContentBinding
import com.example.client.user.domain.User

class GetContentActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityGetContentBinding

    private var user: User? = null
    private var content: Content? = null

    private lateinit var titleTextview: TextView
    private lateinit var textTextview: TextView
    private lateinit var createdAtTextview: TextView
    private lateinit var writerTextview: TextView
    private lateinit var goodImageview: ImageView
    private lateinit var goodTextview: TextView
    private lateinit var badImageView: ImageView
    private lateinit var badTextview: TextView
    private lateinit var modifyButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityGetContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("user") as User
        content = intent.getSerializableExtra("content") as Content

        titleTextview = binding.titleTextview
        textTextview = binding.textTextview
        createdAtTextview = binding.createdAtTextview
        writerTextview = binding.writerTextview
        goodImageview = binding.goodImageview
        goodTextview = binding.goodTextview
        badImageView = binding.badImageView
        badTextview = binding.badTextview
        modifyButton = binding.modifyButton

        titleTextview.text = content!!.title
        textTextview.text = content!!.text
        createdAtTextview.text = content!!.createdAt
        writerTextview.text = content!!.writer
        goodTextview.text = content!!.good.toString()
        badTextview.text = content!!.bad.toString()
    }
}