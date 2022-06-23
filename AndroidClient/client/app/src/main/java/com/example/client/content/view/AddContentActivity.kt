package com.example.client.content.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivityAddContentBinding
import com.example.client.user.domain.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddContentActivity : AppCompatActivity()
{
    lateinit var binding: ActivityAddContentBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}