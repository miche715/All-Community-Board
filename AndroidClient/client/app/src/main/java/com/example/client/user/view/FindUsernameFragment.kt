package com.example.client.user.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.client.databinding.FragmentFindUsernameBinding
import com.example.client.user.service.UserRetrofitServiceObject
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class FindUsernameFragment : Fragment(), CoroutineScope
{
    private var binding: FragmentFindUsernameBinding? = null

    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        job = Job()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentFindUsernameBinding.inflate(inflater, container, false)

        binding!!.findUsernameButton.setOnClickListener()
        {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run()
            {
                this.hideSoftInputFromWindow(binding!!.findUsernameButton.windowToken, 0)
            }

            launch()
            {
                userRetrofitService.findUsername(binding!!.nameEdittext.text.toString(), binding!!.emailEdittext.text.toString()).enqueue(object: Callback<String?>
                {
                    override fun onResponse(call: Call<String?>, response: Response<String?>)
                    {
                        if(response.isSuccessful)
                        {
                            val result = response.body()

                            if(result != null)
                            {
                                Snackbar.make(binding!!.mainLayout, "회원님의 아이디는 ${result} 입니다.", Snackbar.LENGTH_INDEFINITE).run()
                                {
                                    this.setAction("확인", View.OnClickListener()
                                    {
                                        this.dismiss()
                                    })
                                }.show()

                                binding!!.nameEdittext.text.clear()
                                binding!!.emailEdittext.text.clear()
                            }
                            else
                            {
                                Snackbar.make(binding!!.mainLayout, "입력하신 정보의 아이디를 찾을 수 없습니다.", Snackbar.LENGTH_INDEFINITE).run()
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
        }

        return binding!!.root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()

        job.cancel()
        binding = null
    }
}