package com.example.client.content.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.client.R
import com.example.client.content.adapter.ContentListItemAdapter
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivityContentListBinding
import com.example.client.user.domain.User
import com.example.client.user.view.SignInActivity
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentListActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityContentListBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    private lateinit var contentListItemAdapter: ContentListItemAdapter

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityContentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        user = intent.getSerializableExtra("user") as User

        contentListItemAdapter = ContentListItemAdapter(this)
        binding.recylerView.adapter = contentListItemAdapter

        loadRecyclerContent()

        binding.swipeRefreshLayout.setOnRefreshListener()
        {
            with(this)
            {
                this.overridePendingTransition(0, 0)
                this.intent.putExtra("user", user)
                this.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this.intent)
                this.overridePendingTransition(0, 0)

                this.finish()
            }

            binding.swipeRefreshLayout.isRefreshing = false
        }

        // 게시글 읽기 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        contentListItemAdapter.setItemClickListener(object: ContentListItemAdapter.OnItemClickListener
        {
            override fun onClick(v: View, position: Int)
            {
                Intent(this@ContentListActivity, GetContentActivity::class.java).run()
                {
                    this.putExtra("user", user)
                    this.putExtra("content", contentListItemAdapter.contents[position])
                    startActivity(this)
                }
            }
        })
        // 게시글 읽기 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 게시글 생성 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.addContentFab.setOnClickListener()
        {
            Intent(this@ContentListActivity, AddContentActivity::class.java).run()
            {
                this.putExtra("user", user)
                startActivity(this)
            }
        }
        // 게시글 생성 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        contentListItemAdapter.liveEnd.observe(this, Observer()
        {
            if(it)
            {
                contentListItemAdapter.liveEnd.value = false
                page = page + 1

                loadRecyclerContent()
            }
        })
    }

    private var backKeyPressedTime = 0L
    override fun onBackPressed()
    {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000)
        {
            backKeyPressedTime = System.currentTimeMillis()
            Snackbar.make(binding.swipeRefreshLayout, "\'뒤로\'버튼 한번 더 누르시면 종료됩니다.", Snackbar.LENGTH_SHORT).show()

            return
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000)
        {
            finishAffinity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.menu_content_list, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.search -> {
                Intent(this@ContentListActivity, SearchContentListActivity::class.java).run()
                {
                    this.putExtra("user", user)
                    startActivity(this)
                }
            }
            R.id.signOut -> {
                with(AlertDialog.Builder(this))
                {
                    this.setMessage("로그아웃 하시겠습니까?")
                    this.setPositiveButton("확인", DialogInterface.OnClickListener() { _, _ -> signOut() })
                    this.setNegativeButton("취소", DialogInterface.OnClickListener() { _, _ -> })
                }.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private var page = 0
    private fun loadRecyclerContent()
    {
        contentRetrofitService.getAll(page, 15).enqueue(object: Callback<MutableList<Content>>
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<MutableList<Content>>, response: Response<MutableList<Content>>)
            {
                if(response.isSuccessful)
                {
                    contentListItemAdapter.contents.addAll(response.body()!!)
                    contentListItemAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MutableList<Content>>, t: Throwable)
            {
                Log.e("서버 연결 실패", t.toString())
            }
        })
    }

    private fun signOut()
    {
        user = null

        Intent(this@ContentListActivity, SignInActivity::class.java).run()
        {
            startActivity(this)
        }

        finish()
    }
}