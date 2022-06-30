package com.example.client.content.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.content.domain.Content
import com.example.client.content.repository.ContentRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AddContentViewModel(private val contentRepository: ContentRepository = ContentRepository()) : ViewModel()
{
    val result: MutableLiveData<Boolean> = MutableLiveData()  // 결과를 성공적으로 받아오면 여기에 결과가 들어감

    fun addContent(content: Content, userId: Long)
    {
        viewModelScope.launch()
        {
            try
            {
                val response = contentRepository.addContentCheck(content, userId)  // AddContentResponse 리턴
                Log.d("통신 성공", "code = ${response.code}, body = ${response.body}")

                if(response.code == 201 && response.body!!)  // 게시글 생성 성공
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