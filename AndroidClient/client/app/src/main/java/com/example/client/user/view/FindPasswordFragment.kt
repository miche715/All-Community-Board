package com.example.client.user.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.client.databinding.FragmentFindPasswordBinding
import com.example.client.user.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar

class FindPasswordFragment : Fragment()
{
    private var binding: FragmentFindPasswordBinding? = null

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentFindPasswordBinding.inflate(inflater, container, false)

        binding!!.findPasswordButton.setOnClickListener()
        {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding!!.findPasswordButton.windowToken, 0)
            }

            userViewModel.findPassword(binding!!.nameEdittext.text.toString(), binding!!.usernameEdittext.text.toString())
        }
        userViewModel.result.observe(requireActivity())
        {result ->
            Snackbar.make(binding!!.mainLayout, result as String, Snackbar.LENGTH_INDEFINITE).run()
            {
                this.setAction("확인") { this.dismiss() }
            }.show()
        }

        return binding!!.root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()

        binding = null
    }
}