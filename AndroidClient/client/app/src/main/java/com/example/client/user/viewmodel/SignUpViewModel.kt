//package com.example.client.user.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.client.user.domain.SignUpRequest
//import com.example.client.user.repository.UserRepository
//import kotlinx.coroutines.launch
//import java.lang.Exception
//
//class SignUpViewModel(private val userRepository: UserRepository = UserRepository()) : ViewModel()
//{
//    val result: MutableLiveData<Boolean> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감
//    val message: MutableLiveData<String> = MutableLiveData()  // 통신은 성공했는데 뭔가 잘못되어 실패한 경우 메세지
//
//    private lateinit var invalid: String
//    private val nameRegex = "^[가-힣]*$".toRegex()  // 한글만
//    private val usernameRegex = "^[a-z0-9]{2,8}$".toRegex()  // 소문자 + 숫자 2~8자리
//    private val passwordRegex = "^[a-z0-9]{4,20}$".toRegex()  // 소문자 + 숫자 4~20자리
//    private val emailRegex = "^[a-z0-9\\.\\-_]+@([a-z0-9\\-]+\\.)+[a-z]{2,6}$".toRegex()  // 이메일 형식
//
//    fun signUp(request: SignUpRequest)
//    {
//        invalid = ""
//
//        viewModelScope.launch()
//        {
//            try
//            {
//                val response = userRepository.signUpCheck(request)  // SignInResponse 리턴
//
//                if(response.code == 201 && response.body!!)  // 로그인 성공
//                {
//                    result.value = response.body
//                }
//                else  // 로그인 실패
//                {
//                    message.value = "이미 가입된 아이디 또는 이메일 입니다."
//                }
//            }
//            catch(e: Exception)
//            {
//                Log.e("통신 오류", e.message!!)
//            }
//        }
//    }
//}