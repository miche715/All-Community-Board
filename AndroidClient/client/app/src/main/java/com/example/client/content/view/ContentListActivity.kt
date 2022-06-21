package com.example.client.content.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.R
import com.example.client.user.domain.User

class ContentListActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)

        val user = intent.getSerializableExtra("user") as User
        println(user.userId)
        println(user.username)
        println(user.password)
        println(user.name)
        println(user.email)


    }
}