package com.example.client.user.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.client.jsonconverter.NullOnEmptyConverterFactory
import com.example.client.databinding.ActivitySignInBinding
import com.example.client.user.domain.User
import com.example.client.user.service.UserRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignInActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignInBinding

    private val retrofit = Retrofit.Builder()
                           .baseUrl("http://192.168.0.5:8080/users/")
                           .addConverterFactory(NullOnEmptyConverterFactory())
                           .addConverterFactory(GsonConverterFactory.create())
                           .build()
    private val retrofitService = retrofit.create(UserRetrofitService::class.java)

    private lateinit var usernameTextview: TextView
    private lateinit var passwordTextview: TextView
    private lateinit var signInButton: Button
    private lateinit var signUpTextview: TextView
    private lateinit var findAccountTextview: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameTextview = binding.usernameTextview
        passwordTextview = binding.passwordTextview
        signInButton = binding.signInButton
        signUpTextview = binding.signUpTextview
        findAccountTextview = binding.findAccountTextview

        signInButton.setOnClickListener()
        {
            retrofitService.signIn(usernameTextview.text.toString(), passwordTextview.text.toString()).enqueue(object: Callback<User?>
            {
                override fun onResponse(call: Call<User?>, response: Response<User?>)
                {
                    if(response.isSuccessful)
                    {
                        val result = response.body()

                        if(result != null)
                        {
                            println(result)
                        }
                        else
                        {
                            println("아이디 또는 비번 잘못됨")
                        }
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable)
                {
                    Log.e("서버 연결 실패", t.toString())
                }
            })
        }



    }
}