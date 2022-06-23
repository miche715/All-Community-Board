package com.example.client.content.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject
import com.example.client.databinding.ActivityGetContentBinding
import com.example.client.user.domain.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetContentActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityGetContentBinding

    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    private var user: User? = null
    private var content: Content? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityGetContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("user") as User
        content = intent.getSerializableExtra("content") as Content

        binding.titleTextview.text = content!!.title
        binding.textTextview.text = content!!.text
        binding.createdAtTextview.text = content!!.createdAt
        binding.writerTextview.text = content!!.writer
        binding.goodTextview.text = content!!.good.toString()
        binding.badTextview.text = content!!.bad.toString()

        if(user!!.username != content!!.writer)
        {
            binding.modifyButton.visibility = View.INVISIBLE
            binding.deleteButton.visibility = View.INVISIBLE
        }

        binding.modifyButton.setOnClickListener()
        {
            Intent(this@GetContentActivity, ModifyContentActivity::class.java).run()
            {
                this.putExtra("user", user)
                this.putExtra("content", content)
                startActivity(this)
            }
        }

        binding.deleteButton.setOnClickListener()
        {
            with(AlertDialog.Builder(this))
            {
                this.setMessage("게시글을 삭제 하시겠습니까?")
                this.setPositiveButton("확인", DialogInterface.OnClickListener()
                { _, _ ->
                    contentRetrofitService.removeContent(content!!.contentId!!).enqueue(object : Callback<Boolean>
                    {
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>)
                        {
                            if (response.isSuccessful)
                            {
                                if (response.body()!!)
                                {
                                    Intent(this@GetContentActivity, ContentListActivity::class.java).run()
                                    {
                                        startActivity(this)
                                    }

                                    finish()
                                }
                            }
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable)
                        {
                            Log.e("서버 연결 실패", t.toString())
                        }
                    })
                })
                this.setNegativeButton("취소", DialogInterface.OnClickListener() { _, _ -> })
            }.show()
        }
    }
}