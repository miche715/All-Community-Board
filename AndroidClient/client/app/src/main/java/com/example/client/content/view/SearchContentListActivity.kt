package com.example.client.content.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.example.client.content.adapter.ContentListItemAdapter
import com.example.client.content.domain.Content
import com.example.client.content.viewmodel.ContentViewModel
import com.example.client.databinding.ActivitySearchContentListBinding
import com.example.client.user.domain.User
import com.google.android.material.snackbar.Snackbar

class SearchContentListActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySearchContentListBinding

    private val contentViewModel: ContentViewModel by viewModels()

    private lateinit var contentListItemAdapter: ContentListItemAdapter

    private var user: User? = null
    private var keyword: String? = null

    private var page = 0

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
            contentViewModel.getSearch(keyword!!, page, 15)
            contentViewModel.getSearchResult.observe(this)
            {result ->
                @Suppress("UNCHECKED_CAST")
                if(result.size > 0)
                {
                    if(binding.noticeTextview.visibility == View.VISIBLE)
                    {
                        binding.noticeTextview.visibility = View.INVISIBLE
                    }

                    contentListItemAdapter.contents.addAll(result)
                    contentListItemAdapter.notifyDataSetChanged()
                }
                else
                {
                    binding.noticeTextview.text = "검색 결과가 없습니다."
                }
            }
            contentListItemAdapter.liveEnd.observe(this)
            {
                if(it)
                {
                    contentListItemAdapter.liveEnd.value = false
                    page = page + 1

                    contentViewModel.getSearch(keyword!!, page, 15)
                }
            }
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

        contentListItemAdapter.setItemClickListener(object: ContentListItemAdapter.OnItemClickListener
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
}