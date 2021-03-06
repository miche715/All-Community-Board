package com.example.client.content.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.client.R
import com.example.client.content.adapter.ContentListItemAdapter
import com.example.client.content.domain.Content
import com.example.client.content.viewmodel.ContentViewModel
import com.example.client.databinding.ActivityContentListBinding
import com.example.client.user.domain.User
import com.example.client.user.view.SignInActivity
import com.google.android.material.snackbar.Snackbar

class ContentListActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityContentListBinding

    private val contentViewModel: ContentViewModel by viewModels()

    private lateinit var contentListItemAdapter: ContentListItemAdapter

    private var user: User? = null

    private var page = 0

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

        // 15?????? ????????? ?????? ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        contentViewModel.getAll(page, 15)
        contentViewModel.getAllResult.observe(this)
        {result ->
            @Suppress("UNCHECKED_CAST")
            contentListItemAdapter.contents.addAll(result)
            contentListItemAdapter.notifyDataSetChanged()
        }
        contentListItemAdapter.liveEnd.observe(this)
        {
            if(it)
            {
                contentListItemAdapter.liveEnd.value = false
                page = page + 1

                contentViewModel.getAll(page, 15)
            }
        }
        // 15?????? ????????? ?????? ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // ????????? ?????? //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        // ????????? ?????? //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // ????????? ?????? //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.addContentFab.setOnClickListener()
        {
            Intent(this@ContentListActivity, AddContentActivity::class.java).run()
            {
                this.putExtra("user", user)
                startActivity(this)
            }
        }
        // ????????? ?????? //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private var backKeyPressedTime = 0L
    override fun onBackPressed()
    {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000)
        {
            backKeyPressedTime = System.currentTimeMillis()
            Snackbar.make(binding.swipeRefreshLayout, "\'??????\'?????? ?????? ??? ???????????? ???????????????.", Snackbar.LENGTH_SHORT).show()

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
                    this.setMessage("???????????? ???????????????????")
                    this.setPositiveButton("??????") { _, _ -> signOut() }
                    this.setNegativeButton("??????") { _, _ -> }
                }.show()
            }
        }

        return super.onOptionsItemSelected(item)
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