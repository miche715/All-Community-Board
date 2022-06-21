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
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.client.databinding.ActivitySignInBinding
import com.example.client.user.domain.User
import com.example.client.user.service.UserRetrofitServiceObject
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity()
{
    private lateinit var binding: ActivitySignInBinding

    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    private lateinit var usernameEdittext: EditText
    private lateinit var passwordEdittext: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpTextview: TextView
    private lateinit var findAccountTextview: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameEdittext = binding.usernameEdittext
        passwordEdittext = binding.passwordEdittext
        signInButton = binding.signInButton
        signUpTextview = binding.signUpTextview
        findAccountTextview = binding.findAccountTextview

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

        signInButton.setOnClickListener()
        {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(signInButton.windowToken, 0)
            }

            userRetrofitService.signIn(usernameEdittext.text.toString(), passwordEdittext.text.toString()).enqueue(object: Callback<User?>
            {
                override fun onResponse(call: Call<User?>, response: Response<User?>)
                {
                    if(response.isSuccessful)
                    {
                        val result = response.body()

                        if(result != null)
                        {
                            println(result)  // 로그인 성공 후 기능 만들어야함
                        }
                        else
                        {

                        }
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable)
                {
                    Log.e("서버 연결 실패", t.toString())
                }
            })
        }

        signUpTextview.setOnClickListener()
        {
            Intent(this@SignInActivity, SignUpActivity::class.java).run()
            {
                activityResultLauncher.launch(this)
            }
        }

        findAccountTextview.setOnClickListener()
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
}