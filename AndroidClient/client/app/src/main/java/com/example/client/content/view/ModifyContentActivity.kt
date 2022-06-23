package com.example.client.content.view

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivityModifyContentBinding
import com.example.client.user.domain.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class ModifyContentActivity : AppCompatActivity(), CoroutineScope
{
    private lateinit var binding: ActivityModifyContentBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var user: User? = null
    private var content: Content? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityModifyContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("user") as User
        content = intent.getSerializableExtra("content") as Content

        job = Job()

        binding.titleEdittext.setText(content!!.title)
        binding.textEdittext.setText(content!!.text)

        binding.submitButton.setOnClickListener()
        {
            content!!.title = binding.titleEdittext.text.toString()
            content!!.text = binding.textEdittext.text.toString()

            launch()
            {
                contentRetrofitService.modifyContent(content!!, user!!.userId!!).enqueue(object : Callback<Content>
                {
                    override fun onResponse(call: Call<Content>, response: Response<Content>)
                    {
                        if (response.isSuccessful)
                        {
                            Intent(this@ModifyContentActivity, GetContentActivity::class.java).run()
                            {
                                this.putExtra("user", user)
                                this.putExtra("content", response.body())
                                this.addFlags(FLAG_ACTIVITY_CLEAR_TOP)  // 게시글을 수정하고 뒤로가기를 눌렀을 때 수정 하기 전 GetContentActivity가 나와서, 이를 백스택에서 제거하기 위해 사용
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
    }

    override fun onDestroy()
    {
        super.onDestroy()

        job.cancel()
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