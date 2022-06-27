package com.example.client.content.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.example.client.content.adapter.ContentListItemAdapter
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivitySearchContentListBinding
import com.example.client.user.domain.User
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchContentListActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySearchContentListBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    private lateinit var contentListItemAdapter: ContentListItemAdapter

    private var user: User? = null
    private var keyword: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchContentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user = intent.getSerializableExtra("user") as User
        keyword = intent.getStringExtra("keyword")

        contentListItemAdapter = ContentListItemAdapter(this)
        binding.recylerView.adapter = contentListItemAdapter

        if(keyword != null)
        {
            loadRecyclerContent()

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
        else
        {
            binding.noticeTextview.text = "게시글 제목의 일부를 입력 후\n검색 버튼을 눌러주세요."
        }

        binding.searchButton.setOnClickListener()
        {
            if(binding.searchEdittext.text.length < 2)
            {
                Snackbar.make(binding.mainLayout, "두 글자 이상 입력해주세요.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            with(this)
            {
                this.overridePendingTransition(0, 0)
                this.intent.putExtra("user", user)
                this.intent.putExtra("keyword", binding.searchEdittext.text.toString())
                this.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this.intent)
                this.overridePendingTransition(0, 0)

                this.finish()
            }
        }

        contentListItemAdapter.setItemClickListener(object: ContentListItemAdapter.OnItemClickListener  // 게시글 읽기
        {
            override fun onClick(v: View, position: Int)
            {
                Intent(this@SearchContentListActivity, GetContentActivity::class.java).run()
                {
                    this.putExtra("user", user)
                    this.putExtra("content", contentListItemAdapter.contents[position])
                    startActivity(this)
                }
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
        {
            this.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        return true
    }

    private var backKeyPressedTime = 0L
    override fun onBackPressed()
    {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000)
        {
            backKeyPressedTime = System.currentTimeMillis()
            Snackbar.make(binding.mainLayout, "\'뒤로\'버튼 한번 더 누르시면 종료됩니다.", Snackbar.LENGTH_SHORT).show()

            return
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000)
        {
            finishAffinity()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private var page = 0
    private fun loadRecyclerContent()
    {
        contentRetrofitService.getSearch(keyword!!, page, 15).enqueue(object: Callback<MutableList<Content>>
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<MutableList<Content>>, response: Response<MutableList<Content>>)
            {
                if(response.isSuccessful)
                {
                    if(response.body()!!.size > 0)
                    {
                        if(binding.noticeTextview.visibility == View.VISIBLE)
                        {
                            binding.noticeTextview.visibility = View.INVISIBLE
                        }

                        contentListItemAdapter.contents.addAll(response.body()!!)
                        contentListItemAdapter.notifyDataSetChanged()
                    }
                    else
                    {
                        binding.noticeTextview.text = "검색 결과가 없습니다."
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Content>>, t: Throwable)
            {
                Log.e("서버 연결 실패", t.toString())
            }
        })
    }
}