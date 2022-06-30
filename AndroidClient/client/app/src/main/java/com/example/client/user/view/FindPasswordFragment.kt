package com.example.client.user.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.client.databinding.FragmentFindPasswordBinding
import com.example.client.user.viewmodel.FindPasswordViewModel
import com.google.android.material.snackbar.Snackbar

class FindPasswordFragment : Fragment()
{
    private var binding: FragmentFindPasswordBinding? = null

    private val findPasswordViewModel = FindPasswordViewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentFindPasswordBinding.inflate(inflater, container, false)

        binding!!.findPasswordButton.setOnClickListener()
        {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding!!.findPasswordButton.windowToken, 0)
            }

            findPasswordViewModel.findPassword(binding!!.nameEdittext.text.toString(), binding!!.usernameEdittext.text.toString())
        }
        findPasswordViewModel.result.observe(requireActivity())
        {result ->
            Snackbar.make(binding!!.mainLayout, result, Snackbar.LENGTH_INDEFINITE).run()
            {
                this.setAction("확인", View.OnClickListener()
                {
                    this.dismiss()
                })
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