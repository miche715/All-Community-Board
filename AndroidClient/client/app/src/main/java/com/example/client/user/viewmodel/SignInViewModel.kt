package com.example.client.user.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.user.domain.User
import com.example.client.user.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInViewModel(private val userRepository: UserRepository = UserRepository()) : ViewModel()
{
    val result: MutableLiveData<User> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감
    val message: MutableLiveData<String> = MutableLiveData()  // 통신은 성공했는데 뭔가 잘못되어 실패한 경우 메세지

    fun signIn(username: String, password: String)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = userRepository.signInCheck(username, password)  // SignInResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 로그인 성공
                {
                    result.value = response.body
                }
                else  // 로그인 실패
                {
                    message.value = "아이디 또는 패스워드가 잘못됐습니다."
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}