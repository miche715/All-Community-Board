package com.example.client.user.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.client.R
import com.example.client.databinding.ActivityFindAccountBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlin.system.exitProcess

class FindAccountActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityFindAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().replace(R.id.containerFramelayout, FindUsernameFragment()).commit()
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener
        {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                when(tab!!.position)
                {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.containerFramelayout, FindUsernameFragment()).commit()
                    1 -> supportFragmentManager.beginTransaction().replace(R.id.containerFramelayout, FindPasswordFragment()).commit()
                }
            }
        })
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
            moveTaskToBack(true)
            finishAndRemoveTask()

            exitProcess(0)
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