package com.example.client.good.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.content.domain.Content
import com.example.client.good.domain.Good
import com.example.client.good.repository.GoodRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class GoodViewModel(private val goodRepository: GoodRepository = GoodRepository()) : ViewModel()
{
    val addGoodResult: MutableLiveData<Content> = MutableLiveData()
    val addGoodMessage: MutableLiveData<String> = MutableLiveData()

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
                    addGoodResult.value = response.body
                }
                else  // 좋아요 실패
                {
                    addGoodMessage.value = "이미 좋아요를 추가하신 게시글 입니다."
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}