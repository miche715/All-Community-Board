package com.example.client.user.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.user.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class FindPasswordViewModel(private val userRepository: UserRepository = UserRepository()) : ViewModel()
{
    val result: MutableLiveData<String> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감

    fun findPassword(name: String, username: String)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = userRepository.findPasswordCheck(name, username)  // FindPasswordResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 비밀번호 찾기 성공
                {
                    result.value = "회원님의 비밀번호는 ${response.body} 입니다."
                }
                else  // 비밀번호 찾기 실패
                {
                    result.value = "입력하신 정보의 비밀번호를 찾을 수 없습니다."
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}