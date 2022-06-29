package com.example.client.user.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.client.content.view.ContentListActivity
import com.example.client.databinding.ActivitySignUpBinding
import com.example.client.user.domain.User
import com.example.client.user.service.UserRetrofitServiceObject
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SignUpActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignUpBinding

    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    private val nameRegex = "^[가-힣]*$".toRegex()  // 한글만
    private val usernameRegex = "^[a-z0-9]{2,8}$".toRegex()  // 소문자 + 숫자 2~8자리
    private val passwordRegex = "^[a-z0-9]{4,20}$".toRegex()  // 소문자 + 숫자 4~20자리
    private val emailRegex = "^[a-z0-9\\.\\-_]+@([a-z0-9\\-]+\\.)+[a-z]{2,6}$".toRegex()  // 이메일 형식

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.signUpButton.setOnClickListener()
        {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding.signUpButton.windowToken, 0)
            }

            var invalid = ""

            if(!binding.nameEdittext.text.toString().matches(nameRegex) || binding.nameEdittext.text.isEmpty())
            {
                invalid = invalid + "이름 형식이 맞지 않습니다."
            }
            if(!binding.usernameEdittext.text.toString().matches(usernameRegex) || binding.usernameEdittext.text.isEmpty())
            {
                if(invalid.isNotEmpty())
                {
                    invalid = invalid + "\n"
                }
                invalid = invalid + "아이디 형식이 맞지 않습니다."
            }
            if(!binding.passwordEdittext.text.toString().matches(passwordRegex) || binding.passwordEdittext.text.isEmpty())
            {
                if(invalid.isNotEmpty())
                {
                    invalid = invalid + "\n"
                }
                invalid = invalid + "비밀번호 형식이 맞지 않습니다."
            }
            if(binding.passwordEdittext.text.toString() != binding.passwordConfirmEdittext.text.toString())
            {
                if(invalid.isNotEmpty())
                {
                    invalid = invalid + "\n"
                }
                invalid = invalid + "비밀번호가 서로 다릅니다."
            }
            if(!binding.emailEdittext.text.toString().matches(emailRegex) || binding.emailEdittext.text.isEmpty())
            {
                if(invalid.isNotEmpty())
                {
                    invalid = invalid + "\n"
                }
                invalid = invalid + "이메일 형식이 맞지 않습니다."
            }

            if(invalid.isEmpty())
            {
                val user = User().apply()
                {
                    this.username = binding.usernameEdittext.text.toString()
                    this.password = binding.passwordEdittext.text.toString()
                    this.name = binding.nameEdittext.text.toString()
                    this.email = binding.emailEdittext.text.toString()
                }

                CoroutineScope(Dispatchers.IO).launch()
                {
                    try
                    {
                        val result = userRetrofitService.signUp(user)

                        if(result.code == 201 && result.body != null)
                        {
                            if(result.body!!)
                            {
                                withContext(Dispatchers.Main)
                                {
                                    Intent(this@SignUpActivity, SignInActivity::class.java).apply()
                                    {
                                        putExtra("message", "회원 가입이 완료됐습니다.")
                                    }.run()
                                    {
                                        setResult(RESULT_OK, this)
                                    }

                                    finish()
                                }
                            }
                            else
                            {
                                withContext(Dispatchers.Main)
                                {
                                    Snackbar.make(binding.mainLayout, "회원 가입 실패 : ", Snackbar.LENGTH_INDEFINITE).run()
                                    {
                                        this.setAction("이미 가입된 아이디 또는 이메일 입니다.", View.OnClickListener()
                                        {
                                            this.dismiss()
                                        })
                                    }.show()
                                }
                            }
                        }
                    }
                    catch(e: Exception)
                    {
                        Log.e("서버 연결 실패", e.message!!)
                    }
                }
            }
            else
            {
                Snackbar.make(binding.mainLayout, "회원 가입 실패 : ", Snackbar.LENGTH_INDEFINITE).run()
                {
                    this.setAction(invalid, View.OnClickListener()
                    {
                        this.dismiss()
                    })
                }.show()
            }
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