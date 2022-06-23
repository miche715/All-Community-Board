package com.example.client.user.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.client.databinding.FragmentFindPasswordBinding
import com.example.client.user.service.UserRetrofitServiceObject
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindPasswordFragment : Fragment()
{
    private var binding: FragmentFindPasswordBinding? = null

    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentFindPasswordBinding.inflate(inflater, container, false)

        binding!!.findPasswordButton.setOnClickListener()
        {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding!!.findPasswordButton.windowToken, 0)
            }

            userRetrofitService.findPassword(binding!!.nameEdittext.text.toString(), binding!!.usernameEdittext.text.toString()).enqueue(object: Callback<String?>
            {
                override fun onResponse(call: Call<String?>, response: Response<String?>)
                {
                    if(response.isSuccessful)
                    {
                        val result = response.body()

                        if(result != null)
                        {
                            Snackbar.make(binding!!.mainLayout, "회원님의 비밀번호는 ${result} 입니다.", Snackbar.LENGTH_INDEFINITE).run()
                            {
                                this.setAction("확인", View.OnClickListener()
                                {
                                    this.dismiss()
                                })
                            }.show()

                            binding!!.nameEdittext.text.clear()
                            binding!!.usernameEdittext.text.clear()
                        }
                        else
                        {
                            Snackbar.make(binding!!.mainLayout, "입력하신 정보의 비밀번호를 찾을 수 없습니다.", Snackbar.LENGTH_INDEFINITE).run()
                            {
                                this.setAction("확인", View.OnClickListener()
                                {
                                    this.dismiss()
                                })
                            }.show()
                        }
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable)
                {
                    Log.e("서버 연결 실패", t.toString())
                }
            })
        }

        return binding!!.root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()

        binding = null
    }
}