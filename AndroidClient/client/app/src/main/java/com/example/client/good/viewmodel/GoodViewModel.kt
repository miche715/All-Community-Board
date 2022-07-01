package com.example.client.good.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.good.domain.Good
import com.example.client.good.repository.GoodRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class GoodViewModel(private val goodRepository: GoodRepository = GoodRepository()) : ViewModel()
{
    val result: MutableLiveData<Any> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감
    val message: MutableLiveData<String> = MutableLiveData()  // 통신은 성공했는데 뭔가 잘못되어 실패한 경우 메세지

    fun addGood(good: Good, contentId: Long, userId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = goodRepository.addGoodCheck(good, contentId, userId)  // AddGoodResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 201 && response.body != null)  // 좋아요 성공
                {
                    result.value = response.body
                }
                else  // 좋아요 실패
                {
                    message.value = "이미 좋아요를 추가하신 게시글 입니다."
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}