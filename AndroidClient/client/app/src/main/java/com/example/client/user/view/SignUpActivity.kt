package com.example.client.user.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.client.databinding.ActivitySignUpBinding
import com.example.client.user.domain.User
import com.example.client.user.service.UserRetrofitServiceObject
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignUpBinding

    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    private val nameRegex = "^[가-힣]*$".toRegex()  // 한글만
    private val usernameRegex = "^[a-z0-9]{2,8}$".toRegex()  // 소문자 + 숫자 2~8자리
    private val passwordRegex = "^[a-z0-9]{4,20}$".toRegex()  // 소문자 + 숫자 4~20자리
    private val emailRegex = "^[a-z0-9\\.\\-_]+@([a-z0-9\\-]+\\.)+[a-z]{2,6}$".toRegex()  // 이메일 형식

    private lateinit var nameEdittext: EditText
    private lateinit var usernameEdittext: EditText
    private lateinit var passwordEdittext: EditText
    private lateinit var passwordConfirmEdittext: EditText
    private lateinit var emailEdittext: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nameEdittext = binding.nameEdittext
        usernameEdittext = binding.usernameEdittext
        passwordEdittext = binding.passwordEdittext
        passwordConfirmEdittext = binding.passwordConfirmEdittext
        emailEdittext = binding.emailEdittext
        signUpButton = binding.signUpButton

        signUpButton.setOnClickListener()
        {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(signUpButton.windowToken, 0)
            }

            var invalid = ""

            if(!nameEdittext.text.toString().matches(nameRegex) || nameEdittext.text.isEmpty())
            {
                invalid = invalid + "이름 형식이 맞지 않습니다."
            }
            if(!usernameEdittext.text.toString().matches(usernameRegex) || usernameEdittext.text.isEmpty())
            {
                if(invalid.isNotEmpty())
                {
                    invalid = invalid + "\n"
                }
                invalid = invalid + "아이디 형식이 맞지 않습니다."
            }
            if(!passwordEdittext.text.toString().matches(passwordRegex) || passwordEdittext.text.isEmpty())
            {
                if(invalid.isNotEmpty())
                {
                    invalid = invalid + "\n"
                }
                invalid = invalid + "비밀번호 형식이 맞지 않습니다."
            }
            if(passwordEdittext.text.toString() != passwordConfirmEdittext.text.toString())
            {
                if(invalid.isNotEmpty())
                {
                    invalid = invalid + "\n"
                }
                invalid = invalid + "비밀번호가 서로 다릅니다."
            }
            if(!emailEdittext.text.toString().matches(emailRegex) || emailEdittext.text.isEmpty())
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
                    this.username = usernameEdittext.text.toString()
                    this.password = passwordEdittext.text.toString()
                    this.name = nameEdittext.text.toString()
                    this.email = emailEdittext.text.toString()
                }

                userRetrofitService.signUp(user).enqueue(object: Callback<Boolean>
                {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>)
                    {
                        if(response.isSuccessful)
                        {
                            val result = response.body()

                            if(result!!)
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
                            else
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

                    override fun onFailure(call: Call<Boolean>, t: Throwable)
                    {
                        Log.e("서버 연결 실패", t.toString())
                    }
                })
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
}