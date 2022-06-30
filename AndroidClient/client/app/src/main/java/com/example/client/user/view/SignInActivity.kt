package com.example.client.user.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.client.content.view.ContentListActivity
import com.example.client.databinding.ActivitySignInBinding
import com.example.client.user.domain.SignInRequest
import com.example.client.user.viewmodel.SignInViewModel
import com.google.android.material.snackbar.Snackbar

class SignInActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignInBinding

    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    private val signInViewModel = SignInViewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if(it.resultCode == RESULT_OK)
            {
                Snackbar.make(binding.mainLayout, it.data?.getStringExtra("message").toString(), Snackbar.LENGTH_INDEFINITE).run()
                {
                    this.setAction("확인", View.OnClickListener()
                    {
                        this.dismiss()
                    })
                }.show()
            }
        }

        // 로그인 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.signInButton.setOnClickListener()
        {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding.signInButton.windowToken, 0)
            }

            SignInRequest().apply()
            {
                this.username = binding.usernameEdittext.text.toString()
                this.password = binding.passwordEdittext.text.toString()
            }.run()
            {
                signInViewModel.signIn(this)
            }
        }

        signInViewModel.result.observe(this)
        {result ->
            Intent(this@SignInActivity, ContentListActivity::class.java).run()
            {
                this.putExtra("user", result)
                startActivity(this)
            }

            finish()
        }

        signInViewModel.message.observe(this)
        {message ->
            Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_INDEFINITE).run()
            {
                this.setAction("확인", View.OnClickListener()
                {
                    this.dismiss()
                })
            }.show()
        }
        // 로그인 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        binding.signUpTextview.setOnClickListener()  // 회원가입
        {
            Intent(this@SignInActivity, SignUpActivity::class.java).run()
            {
                activityResultLauncher.launch(this)
            }
        }

        binding.findAccountTextview.setOnClickListener()  // 아이디 비밀번호 찾기
        {
            Intent(this@SignInActivity, FindAccountActivity::class.java).run()
            {
                startActivity(this)
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
}