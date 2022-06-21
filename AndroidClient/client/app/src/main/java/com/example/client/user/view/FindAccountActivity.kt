package com.example.client.user.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.example.client.R
import com.example.client.databinding.ActivityFindAccountBinding
import com.google.android.material.tabs.TabLayout

class FindAccountActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityFindAccountBinding

    private lateinit var tabLayout: TabLayout
    private lateinit var findUsernameFragment: FindUsernameFragment
    private lateinit var findPasswordFragment: FindPasswordFragment

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityFindAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.tabLayout
        findUsernameFragment = FindUsernameFragment()
        findPasswordFragment = FindPasswordFragment()

        supportFragmentManager.beginTransaction().replace(R.id.containerFramelayout, findUsernameFragment).commit()
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener
        {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                when(tab!!.position)
                {
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.containerFramelayout, findUsernameFragment).commit()
                    1 -> supportFragmentManager.beginTransaction().replace(R.id.containerFramelayout, findPasswordFragment).commit()
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
}