package com.example.client.content.view

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.example.client.content.domain.Content
import com.example.client.content.viewmodel.ContentViewModel
import com.example.client.databinding.ActivityModifyContentBinding
import com.example.client.user.domain.User
import com.google.android.material.snackbar.Snackbar

class ModifyContentActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityModifyContentBinding

    private val contentViewModel: ContentViewModel by viewModels()

    private var user: User? = null
    private var content: Content? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityModifyContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user = intent.getSerializableExtra("user") as User
        content = intent.getSerializableExtra("content") as Content

        binding.titleEdittext.setText(content!!.title)
        binding.textEdittext.setText(content!!.text)

        binding.submitButton.setOnClickListener()
        {
            content!!.title = binding.titleEdittext.text.toString()
            content!!.text = binding.textEdittext.text.toString()

            contentViewModel.modifyContent(content!!, user!!.userId!!)
        }
        contentViewModel.modifyContentResult.observe(this)
        {result ->
            Intent(this@ModifyContentActivity, GetContentActivity::class.java).run()
            {
                this.putExtra("user", user)
                this.putExtra("content", result)
                this.addFlags(FLAG_ACTIVITY_CLEAR_TOP)  // 게시글을 수정하고 뒤로가기를 눌렀을 때 수정 하기 전 GetContentActivity가 나와서, 이를 백스택에서 제거하기 위해 사용
                startActivity(this)
            }

            finish()
        }
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