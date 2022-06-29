package com.example.client.user.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.client.content.view.ContentListActivity
import com.example.client.databinding.ActivitySignInBinding
import com.example.client.user.service.UserRetrofitServiceObject
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignInActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignInBinding

    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        sharedPreferences = getSharedPreferences("auto", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        if(sharedPreferences.getString("username", null) != null && sharedPreferences.getString("password", null) != null)
        {
            signIn(sharedPreferences.getString("username", null)!!, sharedPreferences.getString("password", null)!!, false)
        }

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

        binding.signInButton.setOnClickListener()
        {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding.signInButton.windowToken, 0)
            }

            signIn(binding.usernameEdittext.text.toString(), binding.passwordEdittext.text.toString(), true)
        }

        binding.signUpTextview.setOnClickListener()
        {
            Intent(this@SignInActivity, SignUpActivity::class.java).run()
            {
                activityResultLauncher.launch(this)
            }
        }

        binding.findAccountTextview.setOnClickListener()
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

    private fun signIn(username: String, password: String, flag: Boolean)
    {
        CoroutineScope(Dispatchers.IO).launch()
        {
            try
            {
                val result = userRetrofitService.signIn(username, password)

                if(result.code == 200 && result.body != null)
                {
                    if(flag) { editor.putString("username", username).putString("password", password).commit() }

                    withContext(Dispatchers.Main)
                    {
                        Intent(this@SignInActivity, ContentListActivity::class.java).run()
                        {
                            this.putExtra("user", result.body)
                            startActivity(this)
                        }

                        finish()
                    }
                }
                else
                {
                    withContext(Dispatchers.Main)
                    {
                        Snackbar.make(binding.mainLayout, "아이디 또는 패스워드가 잘못됐습니다.", Snackbar.LENGTH_INDEFINITE).run()
                        {
                            this.setAction("확인", View.OnClickListener()
                            {
                                this.dismiss()
                            })
                        }.show()
                    }
                }
            }
            catch(e: Exception)
            {
                Log.e("서버 연결 실패", e.message!!)
            }
        }
    }
}