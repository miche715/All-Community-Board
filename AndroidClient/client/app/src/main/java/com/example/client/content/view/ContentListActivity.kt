package com.example.client.content.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.content.adapter.ContentListItemAdapter
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivityContentListBinding
import com.example.client.user.domain.User
import com.example.client.user.view.SignInActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentListActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityContentListBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()
    private lateinit var contentListItemAdapter: ContentListItemAdapter

    private var user: User? = null

    private lateinit var recylerView: RecyclerView
    private lateinit var addContentFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityContentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("user") as User

        recylerView = binding.recylerView
        addContentFab = binding.addContentFab

        addContentFab.setOnClickListener()
        {
            TODO("게시글 쓰는 액티비티 이동 로직 구현해야함.")
        }

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
            R.id.signOut -> {
                with(AlertDialog.Builder(this))
                {
                    this.setMessage("로그 아웃 하시겠습니까?")
                    this.setPositiveButton("확인", DialogInterface.OnClickListener()
                    { _, _ ->
                        user = null

                        Intent(this@ContentListActivity, SignInActivity::class.java).run()
                        {
                            startActivity(this)
                        }

                        finish()
                    })
                    this.setNegativeButton("취소", DialogInterface.OnClickListener() { _, _ -> })
                }.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadRecyclerContent()
    {
        contentListItemAdapter = ContentListItemAdapter(this)
        recylerView.adapter = contentListItemAdapter

        contentRetrofitService.getAll().enqueue(object: Callback<MutableList<Content>>
        {
            @SuppressLint("NotifyDataSetChanged")
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