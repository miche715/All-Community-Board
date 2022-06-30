package com.example.client.user.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.client.databinding.ActivitySignUpBinding
import com.example.client.user.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignUpBinding

    private val signUpViewModel = SignUpViewModel()

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

            signUpViewModel.signUp(binding.usernameEdittext.text.toString(),
                                   binding.passwordEdittext.text.toString(),
                                   binding.passwordConfirmEdittext.text.toString(),
                                   binding.nameEdittext.text.toString(),
                                   binding.emailEdittext.text.toString())
        }
        signUpViewModel.result.observe(this)
        {result ->
            if(result)
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
        signUpViewModel.message.observe(this)
        {message ->
            Snackbar.make(binding.mainLayout, "회원 가입 실패 : ", Snackbar.LENGTH_INDEFINITE).run()
            {
                this.setAction(message, View.OnClickListener()
                {
                    this.dismiss()
                })
            }.show()
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