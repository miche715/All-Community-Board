package com.example.client.content.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivityAddContentBinding
import com.example.client.user.domain.User
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class AddContentActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityAddContentBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user = intent.getSerializableExtra("user") as User

        binding.submitButton.setOnClickListener()
        {
            val content = Content().apply()
            {
                this.writer = user!!.username
                this.title = binding.titleEdittext.text.toString()
                this.text = binding.textEdittext.text.toString()
            }

            contentRetrofitService.addContent(content, user!!.userId!!).enqueue(object: Callback<Content>
            {
                override fun onResponse(call: Call<Content>, response: Response<Content>)
                {
                    if(response.isSuccessful)
                    {
                        Intent(this@AddContentActivity, GetContentActivity::class.java).run()
                        {
                            this.putExtra("user", user)
                            this.putExtra("content", response.body())
                            startActivity(this)
                        }

                        finish()
                    }
                }

                override fun onFailure(call: Call<Content>, t: Throwable)
                {
                    Log.e("서버 연결 실패", t.toString())
                }
            })
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
            moveTaskToBack(true)
            finishAndRemoveTask()

            exitProcess(0)
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