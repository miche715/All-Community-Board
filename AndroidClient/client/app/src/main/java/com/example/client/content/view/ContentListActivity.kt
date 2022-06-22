package com.example.client.content.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.content.adapter.ContentListItemAdapter
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivityContentListBinding
import com.example.client.user.domain.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentListActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityContentListBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()
    private lateinit var contentListItemAdapter: ContentListItemAdapter

    private lateinit var user: User

    lateinit var recylerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityContentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("user") as User

        recylerView = binding.recylerView


    }

    override fun onResume()
    {
        super.onResume()

        loadRecyclerContent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_content_list, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.accountSetting -> println("계정 설정")
            R.id.signOut -> println("로그 아웃")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadRecyclerContent()
    {
        contentListItemAdapter = ContentListItemAdapter(this)
        recylerView.adapter = contentListItemAdapter

        contentRetrofitService.getAll().enqueue(object: Callback<MutableList<Content>>
        {
            override fun onResponse(call: Call<MutableList<Content>>, response: Response<MutableList<Content>>)
            {
                if(response.isSuccessful)
                {
                    contentListItemAdapter.contents = response.body()!!
                    contentListItemAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MutableList<Content>>, t: Throwable)
            {
                Log.e("서버 연결 실패", t.toString())
            }
        })
    }
}