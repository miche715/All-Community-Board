package com.example.client.user.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.client.databinding.FragmentFindUsernameBinding
import com.example.client.user.viewmodel.FindUsernameViewModel
import com.google.android.material.snackbar.Snackbar

class FindUsernameFragment : Fragment()
{
    private var binding: FragmentFindUsernameBinding? = null

    private val findUsernameViewModel = FindUsernameViewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentFindUsernameBinding.inflate(inflater, container, false)

        binding!!.findUsernameButton.setOnClickListener()
        {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding!!.findUsernameButton.windowToken, 0)
            }

            findUsernameViewModel.findUsername(binding!!.nameEdittext.text.toString(), binding!!.emailEdittext.text.toString())
        }
        findUsernameViewModel.result.observe(requireActivity())
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