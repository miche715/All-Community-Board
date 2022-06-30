package com.example.client.content.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.content.domain.Content
import com.example.client.content.repository.ContentRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ModifyContentViewModel(private val contentRepository: ContentRepository = ContentRepository()) : ViewModel()
{
    val result: MutableLiveData<Content> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감

    fun modifyContent(content: Content, userId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = contentRepository.modifyContentCheck(content, userId)  // ModifyContentResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 200 && response.body != null)  // 게시글 수정 성공
                {
                    result.value = response.body
                }
            }
            catch(e: Exception)
            {
                Log.e("통신 오류", e.message!!)
            }
        }
    }
}