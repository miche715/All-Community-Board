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
import androidx.appcompat.app.AlertDialog
import com.example.client.comment.view.CommentListFragment
import com.example.client.content.domain.Content
import com.example.client.content.viewmodel.ContentViewModel
import com.example.client.databinding.ActivityGetContentBinding
import com.example.client.good.domain.Good
import com.example.client.good.viewmodel.GoodViewModel
import com.example.client.user.domain.User
import com.google.android.material.snackbar.Snackbar

class GetContentActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityGetContentBinding

    private val contentViewModel: ContentViewModel by viewModels()
    private val goodViewModel: GoodViewModel by viewModels()

    private var user: User? = null
    private var content: Content? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityGetContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user = intent.getSerializableExtra("user") as User
        content = intent.getSerializableExtra("content") as Content

        binding.titleTextview.text = content!!.title
        binding.textTextview.text = content!!.text
        binding.createdAtTextview.text = content!!.createdAt
        binding.writerTextview.text = content!!.writer
        binding.goodTextview.text = content!!.goodNum.toString()
        binding.commentTextview.text = content!!.commentNum.toString()

        if(user!!.username != content!!.writer)
        {
            binding.modifyButton.visibility = View.INVISIBLE
            binding.deleteButton.visibility = View.INVISIBLE
        }

        // 게시글 수정 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.modifyButton.setOnClickListener()
        {
            Intent(this@GetContentActivity, ModifyContentActivity::class.java).run()
            {
                this.putExtra("user", user)
                this.putExtra("content", content)
                startActivity(this)
            }
        }
        // 게시글 수정 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 게시글 삭제 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.deleteButton.setOnClickListener()
        {
            with(AlertDialog.Builder(this))
            {
                this.setMessage("게시글을 삭제 하시겠습니까?")
                this.setPositiveButton("확인") { _, _ -> contentViewModel.removeContent(content!!.contentId!!) }
                this.setNegativeButton("취소") { _, _ -> }
            }.show()
        }
        contentViewModel.removeContentResult.observe(this)
        {result ->
            if(result)
            {
                Intent(this@GetContentActivity, ContentListActivity::class.java).run()
                {
                    this.putExtra("user", user)
                    startActivity(this)
                }

                finish()
            }
        }
        // 게시글 삭제 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 좋아요 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.goodImageButton.setOnClickListener()
        {
            with(AlertDialog.Builder(this))
            {
                this.setMessage("좋아요를 추가하시겠습니까?")
                this.setPositiveButton("확인") { _, _ -> goodViewModel.addGood(Good(), content!!.contentId!!, user!!.userId!!) }
                this.setNegativeButton("취소") { _, _ -> }
            }.show()
        }
        goodViewModel.addGoodResult.observe(this)
        {result ->
            Intent(this@GetContentActivity, GetContentActivity::class.java).run()
            {
                overridePendingTransition(0, 0)
                this.putExtra("user", user)
                this.putExtra("content", result)
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
                overridePendingTransition(0, 0)
            }

            finish()
        }
        goodViewModel.addGoodMessage.observe(this)
        {message ->
            Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT).show()
        }
        // 좋아요 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        val commentListFragment = CommentListFragment()
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        bundle.putSerializable("content", content)
        commentListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(binding.containerFramelayout.id, commentListFragment).commit()
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