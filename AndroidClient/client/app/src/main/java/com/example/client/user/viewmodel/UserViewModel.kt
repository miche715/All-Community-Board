package com.example.client.user.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.user.domain.User
import com.example.client.user.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(private val userRepository: UserRepository = UserRepository()) : ViewModel()
{
    val result: MutableLiveData<Any> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감
    val message: MutableLiveData<String> = MutableLiveData()  // 통신은 성공했는데 뭔가 잘못되어 실패한 경우 메세지

    private lateinit var invalid: String
    private val nameRegex = "^[가-힣]*$".toRegex()  // 한글만
    private val usernameRegex = "^[a-z0-9]{2,8}$".toRegex()  // 소문자 + 숫자 2~8자리
    private val passwordRegex = "^[a-z0-9]{4,20}$".toRegex()  // 소문자 + 숫자 4~20자리
    private val emailRegex = "^[a-z0-9\\.\\-_]+@([a-z0-9\\-]+\\.)+[a-z]{2,6}$".toRegex()  // 이메일 형식

    fun signUp(username: String, password: String, passwordConfirm: String, name: String, email: String)
    {
        invalid = ""
        if(!name.matches(nameRegex) || name.isEmpty())
        {
            invalid = invalid + "이름 형식이 맞지 않습니다."
        }
        if(!username.matches(usernameRegex) || username.isEmpty())
        {
            if(invalid.isNotEmpty()) { invalid = invalid + "\n" }
            invalid = invalid + "아이디 형식이 맞지 않습니다."
        }
        if(!password.matches(passwordRegex) || password.isEmpty())
        {
            if(invalid.isNotEmpty()) { invalid = invalid + "\n" }
            invalid = invalid + "비밀번호 형식이 맞지 않습니다."
        }
        if(password != passwordConfirm)
        {
            if(invalid.isNotEmpty()) { invalid = invalid + "\n" }
            invalid = invalid + "비밀번호가 서로 다릅니다."
        }
        if(!email.matches(emailRegex) || email.isEmpty())
        {
            if(invalid.isNotEmpty()) { invalid = invalid + "\n" }
            invalid = invalid + "이메일 형식이 맞지 않습니다."
        }

        if(invalid.isEmpty())
        {
            val user = User().apply()
            {
                this.username = username
                this.password = password
                this.name = name
                this.email = email
            }

            viewModelScope.launch()
            {
                try
                {
                    val response = userRepository.signUpCheck(user)  // SignUpResponse 리턴
                    Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                    if(response.code == 201 && response.body!!)  // 회원가입 성공
                    {
                        result.value = response.body
                    }
                    else if(response.code == 200 && !response.body!!) // 회원가입 실패
                    {
                        message.value = "이미 가입된 계정입니다."
                    }
                }
                catch(e: Exception)
                {
                    Log.e("통신 오류", e.message!!)
                }
            }
        }
        else  // 회원가입 실패
        {
            message.value = invalid
        }
    }

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

    fun findUsername(name: String, email: String)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = userRepository.findUsernameCheck(name, email)  // FindUsernameResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 아이디 찾기 성공
                {
                    result.value = "회원님의 아이디는 ${response.body} 입니다."
                }
                else  // 아이디 찾기 실패
                {
                    result.value = "입력하신 정보의 아이디를 찾을 수 없습니다."
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }

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